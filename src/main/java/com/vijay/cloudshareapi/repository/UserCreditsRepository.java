package com.vijay.cloudshareapi.repository;

import com.vijay.cloudshareapi.document.UserCredits;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserCreditsRepository extends MongoRepository<UserCredits, String> {
}
