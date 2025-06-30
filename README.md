# Instapay Clone - Payment Service Provider (PSP)

A Spring Boot application that implements a Payment Service Provider system similar to Instapay, providing secure payment processing and user management capabilities.

## Overview

This project is a clone of Instapay that demonstrates implementation of:

- User authentication and authorization using JWT tokens
- Role-based access control (User/Admin roles)
- RESTful API endpoints
- Secure password handling with BCrypt encryption
- Stateless session management

## Technology Stack

- Java
- Spring Boot
- Spring Security
- JSON Web Tokens (JWT)
- Maven
- BCrypt Password Encoder
- OpenFeign (for HTTP clients)
- Swagger/OpenAPI (API documentation)

## Key Components

### Security

The application implements a robust security layer with:

- JWT-based authentication
- Custom security filters
- Password encryption
- Protected API endpoints
- Stateless session handling

Key classes:
- `SecurityConfig`: Main security configuration
- `JwtService`: Handles JWT operations
- `JwtAuthFilter`: Validates JWT tokens per request

### User Management

Provides comprehensive user management features:

- User registration and authentication
- Role management (User/Admin)
- User profile operations
- Phone number-based user lookup

Key classes:
- `UserServiceImpl`: Core user management logic
- `UserRepository`: Data access layer
- `UserDTO`: Data transfer object for user information

## API Documentation

The API is documented using Swagger/OpenAPI and can be accessed at:
- Swagger UI: `/swagger-ui.html`
- OpenAPI docs: `/v3/api-docs`

## Public Endpoints

The following endpoints are publicly accessible:
- `/api/auth/**`: Authentication endpoints
- `/swagger-ui/**`: Swagger documentation
- `/v3/api-docs/**`: OpenAPI documentation

All other endpoints require authentication.

## Project Structure
```
├─ cib-bank
│  ├─ .gitattributes
│  ├─ .gitignore
│  ├─ .mvn
│  │  └─ wrapper
│  │     └─ maven-wrapper.properties
│  ├─ mvnw
│  ├─ mvnw.cmd
│  ├─ pom.xml
│  └─ src
│     ├─ main
│     │  ├─ java
│     │  │  └─ com
│     │  │     └─ psp
│     │  │        └─ cibbank
│     │  │           ├─ CibBankApplication.java
│     │  │           ├─ common
│     │  │           │  ├─ exception
│     │  │           │  │  ├─ AccountNotFoundException.java
│     │  │           │  │  ├─ CardNotFoundException.java
│     │  │           │  │  ├─ CustomerNotFoundException.java
│     │  │           │  │  └─ TransactionException.java
│     │  │           │  └─ util
│     │  │           │     └─ EncryptionUtil.java
│     │  │           ├─ controller
│     │  │           │  ├─ AccountController.java
│     │  │           │  ├─ CustomerController.java
│     │  │           │  ├─ GlobalExceptionHandler.java
│     │  │           │  └─ TransactionController.java
│     │  │           ├─ model
│     │  │           │  ├─ dto
│     │  │           │  │  ├─ CustomerDTO.java
│     │  │           │  │  ├─ request
│     │  │           │  │  │  ├─ GetAccountsRequest.java
│     │  │           │  │  │  └─ TransactionRequest.java
│     │  │           │  │  └─ response
│     │  │           │  │     ├─ ApiResponse.java
│     │  │           │  │     ├─ GetAccountsResponse.java
│     │  │           │  │     └─ TransactionResponse.java
│     │  │           │  ├─ entity
│     │  │           │  │  ├─ Account.java
│     │  │           │  │  ├─ Card.java
│     │  │           │  │  ├─ Customer.java
│     │  │           │  │  └─ Transaction.java
│     │  │           │  ├─ enums
│     │  │           │  │  ├─ CardType.java
│     │  │           │  │  ├─ TransactionStatus.java
│     │  │           │  │  └─ TransactionType.java
│     │  │           │  ├─ repository
│     │  │           │  │  ├─ AccountRepository.java
│     │  │           │  │  ├─ CardRepository.java
│     │  │           │  │  ├─ CustomerRepository.java
│     │  │           │  │  └─ TransactionRepository.java
│     │  │           │  └─ service
│     │  │           │     ├─ AccountService.java
│     │  │           │     ├─ CustomerService.java
│     │  │           │     ├─ TransactionService.java
│     │  │           │     └─ impl
│     │  │           │        ├─ AccountServiceImpl.java
│     │  │           │        ├─ CustomerServiceImpl.java
│     │  │           │        └─ TransactionServiceImpl.java
│     │  │           └─ security
│     │  │              ├─ ApiKeyAuthFilter.java
│     │  │              ├─ ApiKeyAuthenticationToken.java
│     │  │              └─ SecurityConfig.java
│     │  └─ resources
│     │     ├─ application.yml
│     │     └─ banner.txt
│     └─ test
│        └─ java
│           └─ com
│              └─ psp
│                 └─ nbebank
│                    └─ CibBankApplicationTests.java
├─ instapay
│  ├─ .gitattributes
│  ├─ .gitignore
│  ├─ .mvn
│  │  └─ wrapper
│  │     └─ maven-wrapper.properties
│  ├─ mvnw
│  ├─ mvnw.cmd
│  ├─ pom.xml
│  └─ src
│     ├─ main
│     │  ├─ java
│     │  │  └─ com
│     │  │     └─ psp
│     │  │        └─ instapay
│     │  │           ├─ InstapayApplication.java
│     │  │           ├─ common
│     │  │           │  ├─ client
│     │  │           │  │  ├─ BankClient.java
│     │  │           │  │  ├─ BankClientFactory.java
│     │  │           │  │  ├─ banks
│     │  │           │  │  │  ├─ CIBClient.java
│     │  │           │  │  │  └─ NBEClient.java
│     │  │           │  │  └─ config
│     │  │           │  │     ├─ CibClientConfig.java
│     │  │           │  │     └─ NbeClientConfig.java
│     │  │           │  ├─ exception
│     │  │           │  │  ├─ AccountAlreadyExistsException.java
│     │  │           │  │  ├─ AccountNotFoundException.java
│     │  │           │  │  ├─ BankNotFoundException.java
│     │  │           │  │  ├─ CardNotFoundException.java
│     │  │           │  │  ├─ CustomerNotFoundException.java
│     │  │           │  │  ├─ InsufficientBalanceException.java
│     │  │           │  │  ├─ InvalidRoleException.java
│     │  │           │  │  ├─ TransactionException.java
│     │  │           │  │  └─ UserNotFoundException.java
│     │  │           │  ├─ mapper
│     │  │           │  │  ├─ AccountMapper.java
│     │  │           │  │  ├─ TransactionMapper.java
│     │  │           │  │  └─ UserMapper.java
│     │  │           │  └─ util
│     │  │           │     └─ EncryptionUtil.java
│     │  │           ├─ controller
│     │  │           │  ├─ AccountController.java
│     │  │           │  ├─ AuthenticationController.java
│     │  │           │  ├─ GlobalExceptionHandler.java
│     │  │           │  ├─ TransactionController.java
│     │  │           │  └─ UserController.java
│     │  │           ├─ model
│     │  │           │  ├─ dto
│     │  │           │  │  ├─ AccountDTO.java
│     │  │           │  │  ├─ BankDTO.java
│     │  │           │  │  ├─ TransactionDTO.java
│     │  │           │  │  ├─ UserDTO.java
│     │  │           │  │  ├─ request
│     │  │           │  │  │  ├─ AccountDetailsRequest.java
│     │  │           │  │  │  ├─ GetAccountsRequest.java
│     │  │           │  │  │  ├─ LoginRequest.java
│     │  │           │  │  │  ├─ SendMoneyRequest.java
│     │  │           │  │  │  ├─ SignUpRequest.java
│     │  │           │  │  │  ├─ TransactionRequest.java
│     │  │           │  │  │  └─ UpdateRoleRequest.java
│     │  │           │  │  └─ response
│     │  │           │  │     ├─ ApiResponse.java
│     │  │           │  │     ├─ AuthenticationResponse.java
│     │  │           │  │     ├─ GetAccountsResponse.java
│     │  │           │  │     └─ TransactionResponse.java
│     │  │           │  ├─ entity
│     │  │           │  │  ├─ Account.java
│     │  │           │  │  ├─ Bank.java
│     │  │           │  │  ├─ Transaction.java
│     │  │           │  │  └─ User.java
│     │  │           │  ├─ enums
│     │  │           │  │  ├─ Role.java
│     │  │           │  │  ├─ TransactionStatus.java
│     │  │           │  │  └─ TransactionType.java
│     │  │           │  ├─ repository
│     │  │           │  │  ├─ AccountRepository.java
│     │  │           │  │  ├─ BankRepository.java
│     │  │           │  │  ├─ TransactionRepository.java
│     │  │           │  │  └─ UserRepository.java
│     │  │           │  └─ service
│     │  │           │     ├─ AccountService.java
│     │  │           │     ├─ AuthenticationService.java
│     │  │           │     ├─ TransactionService.java
│     │  │           │     ├─ UserService.java
│     │  │           │     └─ impl
│     │  │           │        ├─ AccountServiceImpl.java
│     │  │           │        ├─ AuthenticationServiceImpl.java
│     │  │           │        ├─ TransactionServiceImpl.java
│     │  │           │        └─ UserServiceImpl.java
│     │  │           └─ security
│     │  │              ├─ SecurityConfig.java
│     │  │              └─ jwt
│     │  │                 ├─ JwtAuthFilter.java
│     │  │                 └─ JwtService.java
│     │  └─ resources
│     │     ├─ application.yml
│     │     └─ banner.txt
│     └─ test
│        └─ java
│           └─ com
│              └─ psp
│                 └─ instapay
│                    └─ InstapayApplicationTests.java
└─ nbe-bank
   ├─ .gitattributes
   ├─ .gitignore
   ├─ .mvn
   │  └─ wrapper
   │     └─ maven-wrapper.properties
   ├─ mvnw
   ├─ mvnw.cmd
   ├─ pom.xml
   └─ src
      ├─ main
      │  ├─ java
      │  │  └─ com
      │  │     └─ psp
      │  │        └─ nbebank
      │  │           ├─ NbeBankApplication.java
      │  │           ├─ common
      │  │           │  ├─ exception
      │  │           │  │  ├─ AccountNotFoundException.java
      │  │           │  │  ├─ CardNotFoundException.java
      │  │           │  │  ├─ CustomerNotFoundException.java
      │  │           │  │  └─ TransactionException.java
      │  │           │  └─ util
      │  │           │     └─ EncryptionUtil.java
      │  │           ├─ controller
      │  │           │  ├─ AccountController.java
      │  │           │  ├─ CustomerController.java
      │  │           │  ├─ GlobalExceptionHandler.java
      │  │           │  └─ TransactionController.java
      │  │           ├─ model
      │  │           │  ├─ dto
      │  │           │  │  ├─ CustomerDTO.java
      │  │           │  │  ├─ request
      │  │           │  │  │  ├─ GetAccountsRequest.java
      │  │           │  │  │  └─ TransactionRequest.java
      │  │           │  │  └─ response
      │  │           │  │     ├─ ApiResponse.java
      │  │           │  │     ├─ GetAccountsResponse.java
      │  │           │  │     └─ TransactionResponse.java
      │  │           │  ├─ entity
      │  │           │  │  ├─ Account.java
      │  │           │  │  ├─ Card.java
      │  │           │  │  ├─ Customer.java
      │  │           │  │  └─ Transaction.java
      │  │           │  ├─ enums
      │  │           │  │  ├─ CardType.java
      │  │           │  │  ├─ TransactionStatus.java
      │  │           │  │  └─ TransactionType.java
      │  │           │  ├─ repository
      │  │           │  │  ├─ AccountRepository.java
      │  │           │  │  ├─ CardRepository.java
      │  │           │  │  ├─ CustomerRepository.java
      │  │           │  │  └─ TransactionRepository.java
      │  │           │  └─ service
      │  │           │     ├─ AccountService.java
      │  │           │     ├─ CustomerService.java
      │  │           │     ├─ TransactionService.java
      │  │           │     └─ impl
      │  │           │        ├─ AccountServiceImpl.java
      │  │           │        ├─ CustomerServiceImpl.java
      │  │           │        └─ TransactionServiceImpl.java
      │  │           └─ security
      │  │              ├─ ApiKeyAuthFilter.java
      │  │              ├─ ApiKeyAuthenticationToken.java
      │  │              └─ SecurityConfig.java
      │  └─ resources
      │     ├─ application.yml
      │     └─ banner.txt
      └─ test
         └─ java
            └─ com
               └─ psp
                  └─ nbebank
                     └─ NbeBankApplicationTests.java
```
