# CloudShare — File Sharing Platform Backend

> Secure REST API for CloudShare file sharing platform built with Spring Boot, MongoDB, and Clerk JWT authentication.

---

## Table of Contents

- [Overview](#overview)
- [Key Features](#key-features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [API Reference](#api-reference)
- [Technical Highlights](#technical-highlights)
- [Author](#author)

---

## Overview

CloudShare is a secure file sharing backend that allows users to upload, manage, and share files through a clean REST API. Built with Spring Boot and MongoDB, it supports public/private file visibility, secure user authentication via Clerk JWT, and subscription-based features powered by Razorpay.

The system is designed with security and scalability in mind, providing a solid foundation for cloud-based file management platforms.

---

## Key Features

| Feature | Description |
| --- | --- |
| **File Management** | Upload, view, download, and delete files |
| **Visibility Control** | Toggle files between public and private access |
| **Secure Sharing** | Share files via public links without exposing private data |
| **Clerk JWT Auth** | Secure user authentication integrated with Spring Security |
| **Razorpay Payments** | Subscription-based feature access via payment gateway |
| **RESTful API Design** | Clean, resource-oriented REST endpoints |

---

## Tech Stack

| Layer | Technology |
| --- | --- |
| Language | Java |
| Framework | Spring Boot |
| Security | Spring Security + Clerk JWT |
| Database | MongoDB |
| ODM | Spring Data MongoDB |
| Build Tool | Maven |
| API Testing | Postman |
| Payments | Razorpay |

---

## Architecture

The project follows a structured layered architecture to ensure clean separation of concerns and maintainability:

```
Controller  →  Service  →  Repository  →  MongoDB
     ↑              ↑
    DTO          Mapper
```

**Architectural Highlights:**

- **Controller Layer** — Handles HTTP requests, input validation, and response formatting via DTOs
- **Service Layer** — Encapsulates all business logic for file and user operations
- **Repository Layer** — Manages database access through Spring Data MongoDB interfaces
- **DTO Layer** — Decouples API contracts from internal domain models
- **Mapper Layer** — Handles bidirectional conversion between documents and DTOs

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+
- MongoDB 6.0+
- Clerk account for JWT configuration
- Razorpay account for payment integration

### Installation

```bash
# 1. Clone the repository
git clone https://github.com/dvijaykumarr/cloudshare-backend.git

# 2. Navigate into the project directory
cd cloudshare-backend

# 3. Configure your environment
# Edit src/main/resources/application.properties
spring.data.mongodb.uri=your_mongodb_uri
clerk.jwks.url=your_clerk_jwks_url
razorpay.key.id=your_razorpay_key_id
razorpay.key.secret=your_razorpay_key_secret

# 4. Build the project
mvn clean install

# 5. Run the application
mvn spring-boot:run
```

The server will start at `http://localhost:8080`.

---

## API Reference

Base URL: `http://localhost:8080`

> All endpoints (except public file access) require a valid Clerk JWT token passed as a Bearer token in the Authorization header.

### File Management

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/files/upload` | Upload a new file |
| `GET` | `/api/files` | Get all files for current user |
| `GET` | `/api/files/{id}` | Get file by ID |
| `DELETE` | `/api/files/{id}` | Delete a file |
| `PATCH` | `/api/files/{id}/visibility` | Toggle file visibility (public/private) |
| `GET` | `/api/files/public/{token}` | Access a file via public share link |

### Subscriptions & Payments

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/payments/order` | Create a Razorpay payment order |
| `POST` | `/api/payments/verify` | Verify payment and activate subscription |
| `GET` | `/api/subscriptions/status` | Get current user subscription status |

---

## Technical Highlights

- Integrated **Clerk JWT authentication** with Spring Security to protect all private routes while allowing public file access via tokenized share links
- Designed **public/private visibility control** at the file level, enabling granular access management per user
- Implemented **Razorpay payment gateway** for subscription-based feature unlocking with server-side payment verification
- Used **Spring Data MongoDB** for flexible document-based storage, suited for file metadata management
- Followed **layered architecture principles** (Controller → Service → Repository) to ensure separation of concerns and maintainability
- Applied **DTO and Mapper patterns** to decouple the API contract from internal domain models

---

## Author

**Daarivemula Vijay Kumar**

- Email: [dvk200507@gmail.com](mailto:dvk200507@gmail.com)
- LinkedIn: [linkedin.com/in/dvijaykumarr](https://linkedin.com/in/dvijaykumarr)
- GitHub: [github.com/dvijaykumarr](https://github.com/dvijaykumarr)

---

> Built with a focus on secure file handling, clean API design, and real-world payment integration.
