FinCore Wallet System

A secure, scalable digital wallet and mini-core banking backend system built with Java, Spring Boot, and PostgreSQL.
This project simulates real-world fintech wallet operations such as user onboarding, wallet funding, transfers, and transaction tracking.


Project Overview

FinCore Wallet System is designed to replicate core financial operations found in modern digital banking and fintech platforms.

It focuses on:

 Transaction integrity
 Secure authentication & authorization
 Scalable backend architecture
 Auditability and traceability of financial operations

This project is being built as part of a continuous learning and professional branding initiative focused on enterprise backend engineering and fintech systems design.



System Architecture

The system follows a layered architecture:

Controller Layer → Service Layer → Repository Layer → Database

 Modular Structure

com.fincore.wallet
│
├── auth             Authentication & Authorization (JWT)
├── user             User management
├── wallet           Wallet operations
├── transaction      Transfers, credit, debit logic
├── notification     Event/notification handling
├── audit            Transaction logs & auditing
├── config           Application configuration
├── security         Security configuration (Spring Security)
├── exception       Global exception handling
└── common           Shared utilities & constants


Tech Stack
 Backend:
 Java 21
 Spring Boot 3
 Spring Security
 Spring Data JPA
 Hibernate

 Database: MySQL

 Authentication: JSON Web Token (JWT)

 Tools & Infrastructure

 Maven
 Swagger / OpenAPI
 Docker (planned)
 Redis (planned)
 RabbitMQ/Kafka (planned)

Key Features

User Management

 User registration
 Secure login
 Role-based access control (USER / ADMIN)

Wallet Management

 Wallet creation per user
 Account number generation
 Wallet balance tracking

Transactions

 Wallet-to-wallet transfers
 Credit and debit operations
 Transaction reference generation
 Transaction history tracking

Audit & Traceability

 Full transaction logs
 Event tracking for financial operations
 Failure and success state tracking

Security

 JWT-based authentication
 Role-based authorization
 Password encryption (BCrypt)
 Stateless session management

Core Business Rules
 A wallet cannot go below zero balance
 Every transaction must have a unique reference ID
 Transfers must be atomic (all-or-nothing)
 All financial operations are auditable
 Users can only access their own wallet data unless admin


API Documentation

Swagger UI will be available at:


http://localhost:8080/swagger-ui/index.html

Sample API Endpoints

Authentication
POST /api/auth/register
POST /api/auth/login


 Wallet

POST /api/wallet/create
GET  /api/wallet/balance

Transactions

POST /api/transaction/transfer
GET  /api/transaction/history

Setup & Installation

1. Clone Repository

bash
git clone https://github.com/your-username/fincore-wallet.git
cd fincore-wallet

2. Configure Database

Update `application.properties`:

properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fincore_wallet
spring.datasource.username=postgres
spring.datasource.password=your_password

3. Run Application

bash
mvn spring-boot:run

Docker Setup (Coming Soon)

Planned support for containerized deployment:

 Spring Boot App Container
 PostgreSQL Container
 Redis Cache Container

Future Enhancements

 🔄 Event-driven architecture (Kafka/RabbitMQ)
 ⚡ Redis caching for wallet balances
 📊 Admin analytics dashboard
 💳 Multi-currency wallet support
 🧠 Fraud detection rules engine
 📱 Mobile API optimization layer
 ☁️ Cloud deployment (AWS / Render / Railway)

Key Engineering Concepts Demonstrated

This project demonstrates understanding of:

 Fintech transaction systems
 ACID properties in financial operations
 Secure backend system design
 Scalable REST API architecture
 Authentication & authorization models
 Domain-driven design (DDD principles)
 Production-grade error handling
 Audit logging for compliance systems

Author

Inibehe Ekanem
Backend Engineer | Fintech Systems Enthusiast | Spring Boot Developer

Project Goal
This project is part of a deliberate effort to build real-world enterprise backend systems and document the learning journey publicly.
