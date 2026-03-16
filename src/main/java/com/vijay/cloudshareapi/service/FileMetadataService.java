package com.vijay.cloudshareapi.service;

import com.vijay.cloudshareapi.document.FileMetadataDocument;
import com.vijay.cloudshareapi.document.ProfileDocument;
import com.vijay.cloudshareapi.dto.FileMetadataDTO;
import com.vijay.cloudshareapi.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileMetadataService {

    private final ProfileService profileService;
    private final UserCreditsService userCreditsService;
    private final FileMetadataRepository fileMetadataRepository;

    public List<FileMetadataDTO> uploadFiles(MultipartFile files[]) throws IOException {

        List<FileMetadataDocument> savedFiles = new ArrayList<>();

        ProfileDocument currentProfile = profileService.getCurrentProfile();

        if(!userCreditsService.hasEnoughCredits(files.length)){
            throw  new RuntimeException("Not enough credits to upload files. Please buy credits");
        }
        Path uploadPath = Paths.get("upload").toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        for(MultipartFile file : files){
            String fileName = UUID.randomUUID() + "."+ StringUtils.getFilename(file.getOriginalFilename());
            Path targetLocation = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            FileMetadataDocument fileMetadata = FileMetadataDocument.builder()
                    .fileLocation(targetLocation.toString())
                    .name(file.getOriginalFilename())
                    .size(file.getSize())
                    .type(file.getContentType())
                    .clerkId(currentProfile.getClerkId())
                    .isPublic(false)
                    .uploadedAt(LocalDateTime.now())
                    .build();

            //todo: consume one credit for each file upload
            userCreditsService.consumeCredits();

            savedFiles.add(fileMetadataRepository.save(fileMetadata));

        }

        return savedFiles.stream().map(fileMetadataDocument -> mapToDTO(fileMetadataDocument))
                .collect(Collectors.toList());


    }

    private FileMetadataDTO mapToDTO(FileMetadataDocument fileMetadataDocument) {
        return FileMetadataDTO.builder()
                .id(fileMetadataDocument.getId())
                .fileLocation(fileMetadataDocument.getFileLocation())
                .name(fileMetadataDocument.getName())
                .size(fileMetadataDocument.getSize())
                .type(fileMetadataDocument.getType())
                .clerkId(fileMetadataDocument.getClerkId())
                .isPublic(fileMetadataDocument.getIsPublic())
                .uploadedAt(fileMetadataDocument.getUploadedAt())
                .build();
    }

    public List<FileMetadataDTO> getFiles() {
        ProfileDocument currentProfile = profileService.getCurrentProfile();
        List<FileMetadataDocument> files = fileMetadataRepository.findByClerkId(currentProfile.getClerkId());
        return files.stream().map(this::mapToDTO).collect(Collectors.toList());
    }


}
