# 💸 Instapay PSP Clone — Distributed Payment Service Provider 🚀

Instapay PSP Clone is a robust, distributed microservice-based Payment Service Provider (PSP) platform that simulates secure interbank money transfers — inspired by Egypt’s national Instapay system 🇪🇬. 

This project demonstrates real-world **backend architecture and coordination**, showcasing secure **multi-bank integration**, **distributed transactions with two-phase commit**, and microservice communication using **OpenFeign**.

---

## 🎯 Purpose and Goals

The goal of this system is to model how a real-world PSP coordinates money transfers between customers’ accounts in different banks, while ensuring **consistency, security, and scalability**.

This includes:

- 🌐 **Interacting with multiple banks** using APIs
- 🔐 **User authentication via JWT**
- 🔑 **Bank service security via API keys**
- 🔁 **Ensuring atomic money transfers** with Two-Phase Commit (2PC)
- 🧱 Demonstrating **modular microservice architecture**
- 💬 Implementing clean inter-service communication with **OpenFeign**

---

## 🧩 System Architecture

```
project-root/
├── instapay/ 🛠️ Orchestrates all user & transaction operations
├── cib-bank/ 🏦 Mock Bank #1 with own DB and API key security
├── nbe-bank/ 🏦 Mock Bank #2 with same capabilities as CIB
```


Each microservice is an independent Spring Boot application with its own:
- Database
- Repository layer
- Authentication configuration
- REST controllers and services

All bank services respond to commands from the **Instapay orchestrator**, which coordinates money transfers via 2PC (Two-Phase Commit) for consistency and fault tolerance.

---

## 🏦 Mock Banks Architecture

```
├─ xxx-bank
│  ├─ .gitattributes
│  ├─ .gitignore
│  ├─ pom.xml
│  └─ src
│     ├─ main
│     │  ├─ java
│     │  │  └─ com
│     │  │     └─ psp
│     │  │        └─ cibbank
│     │  │           ├─ XxxBankApplication.java
│     │  │           ├─ common
│     │  │           │  ├─ exception
│     │  │           │  │  ├─ AccountNotFoundException.java
│     │  │           │  │  ├─ CardNotFoundException.java
│     │  │           │  │  ├─ CustomerNotFoundException.java
│     │  │           │  │  └─ TransactionException.java
│     │  │           │  └─ util
│     │  │           │     └─ EncryptionUtil.java
│     │  │           ├─ controller
│     │  │           │  ├─ AccountController.java
│     │  │           │  ├─ CustomerController.java
│     │  │           │  ├─ GlobalExceptionHandler.java
│     │  │           │  └─ TransactionController.java
│     │  │           ├─ model
│     │  │           │  ├─ dto
│     │  │           │  │  ├─ CustomerDTO.java
│     │  │           │  │  ├─ request
│     │  │           │  │  │  ├─ GetAccountsRequest.java
│     │  │           │  │  │  └─ TransactionRequest.java
│     │  │           │  │  └─ response
│     │  │           │  │     ├─ ApiResponse.java
│     │  │           │  │     ├─ GetAccountsResponse.java
│     │  │           │  │     └─ TransactionResponse.java
│     │  │           │  ├─ entity
│     │  │           │  │  ├─ Account.java
│     │  │           │  │  ├─ Card.java
│     │  │           │  │  ├─ Customer.java
│     │  │           │  │  └─ Transaction.java
│     │  │           │  ├─ enums
│     │  │           │  │  ├─ CardType.java
│     │  │           │  │  ├─ TransactionStatus.java
│     │  │           │  │  └─ TransactionType.java
│     │  │           │  ├─ repository
│     │  │           │  │  ├─ AccountRepository.java
│     │  │           │  │  ├─ CardRepository.java
│     │  │           │  │  ├─ CustomerRepository.java
│     │  │           │  │  └─ TransactionRepository.java
│     │  │           │  └─ service
│     │  │           │     ├─ AccountService.java
│     │  │           │     ├─ CustomerService.java
│     │  │           │     ├─ TransactionService.java
│     │  │           │     └─ impl
│     │  │           │        ├─ AccountServiceImpl.java
│     │  │           │        ├─ CustomerServiceImpl.java
│     │  │           │        └─ TransactionServiceImpl.java
│     │  │           └─ security
│     │  │              ├─ ApiKeyAuthFilter.java
│     │  │              ├─ ApiKeyAuthenticationToken.java
│     │  │              └─ SecurityConfig.java
│     │  └─ resources
│     │     ├─ application.yml
│     │     └─ banner.txt
```

---

## 🔐 Instapay Architecture

```
├─ instapay
│  ├─ .gitattributes
│  ├─ .gitignore
│  ├─ pom.xml
│  └─ src
│     ├─ main
│     │  ├─ java
│     │  │  └─ com
│     │  │     └─ psp
│     │  │        └─ instapay
│     │  │           ├─ InstapayApplication.java
│     │  │           ├─ common
│     │  │           │  ├─ client
│     │  │           │  │  ├─ BankClient.java
│     │  │           │  │  ├─ BankClientFactory.java
│     │  │           │  │  ├─ banks
│     │  │           │  │  │  ├─ CIBClient.java
│     │  │           │  │  │  └─ NBEClient.java
│     │  │           │  │  └─ config
│     │  │           │  │     ├─ CibClientConfig.java
│     │  │           │  │     └─ NbeClientConfig.java
│     │  │           │  ├─ exception
│     │  │           │  │  ├─ AccountAlreadyExistsException.java
│     │  │           │  │  ├─ AccountNotFoundException.java
│     │  │           │  │  ├─ BankNotFoundException.java
│     │  │           │  │  ├─ CardNotFoundException.java
│     │  │           │  │  ├─ CustomerNotFoundException.java
│     │  │           │  │  ├─ InsufficientBalanceException.java
│     │  │           │  │  ├─ InvalidRoleException.java
│     │  │           │  │  ├─ TransactionException.java
│     │  │           │  │  └─ UserNotFoundException.java
│     │  │           │  ├─ mapper
│     │  │           │  │  ├─ AccountMapper.java
│     │  │           │  │  ├─ TransactionMapper.java
│     │  │           │  │  └─ UserMapper.java
│     │  │           │  └─ util
│     │  │           │     └─ EncryptionUtil.java
│     │  │           ├─ controller
│     │  │           │  ├─ AccountController.java
│     │  │           │  ├─ AuthenticationController.java
│     │  │           │  ├─ GlobalExceptionHandler.java
│     │  │           │  ├─ TransactionController.java
│     │  │           │  └─ UserController.java
│     │  │           ├─ model
│     │  │           │  ├─ dto
│     │  │           │  │  ├─ AccountDTO.java
│     │  │           │  │  ├─ BankDTO.java
│     │  │           │  │  ├─ TransactionDTO.java
│     │  │           │  │  ├─ UserDTO.java
│     │  │           │  │  ├─ request
│     │  │           │  │  │  ├─ AccountDetailsRequest.java
│     │  │           │  │  │  ├─ GetAccountsRequest.java
│     │  │           │  │  │  ├─ LoginRequest.java
│     │  │           │  │  │  ├─ SendMoneyRequest.java
│     │  │           │  │  │  ├─ SignUpRequest.java
│     │  │           │  │  │  ├─ TransactionRequest.java
│     │  │           │  │  │  └─ UpdateRoleRequest.java
│     │  │           │  │  └─ response
│     │  │           │  │     ├─ ApiResponse.java
│     │  │           │  │     ├─ AuthenticationResponse.java
│     │  │           │  │     ├─ GetAccountsResponse.java
│     │  │           │  │     └─ TransactionResponse.java
│     │  │           │  ├─ entity
│     │  │           │  │  ├─ Account.java
│     │  │           │  │  ├─ Bank.java
│     │  │           │  │  ├─ Transaction.java
│     │  │           │  │  └─ User.java
│     │  │           │  ├─ enums
│     │  │           │  │  ├─ Role.java
│     │  │           │  │  ├─ TransactionStatus.java
│     │  │           │  │  └─ TransactionType.java
│     │  │           │  ├─ repository
│     │  │           │  │  ├─ AccountRepository.java
│     │  │           │  │  ├─ BankRepository.java
│     │  │           │  │  ├─ TransactionRepository.java
│     │  │           │  │  └─ UserRepository.java
│     │  │           │  └─ service
│     │  │           │     ├─ AccountService.java
│     │  │           │     ├─ AuthenticationService.java
│     │  │           │     ├─ TransactionService.java
│     │  │           │     ├─ UserService.java
│     │  │           │     └─ impl
│     │  │           │        ├─ AccountServiceImpl.java
│     │  │           │        ├─ AuthenticationServiceImpl.java
│     │  │           │        ├─ TransactionServiceImpl.java
│     │  │           │        └─ UserServiceImpl.java
│     │  │           └─ security
│     │  │              ├─ SecurityConfig.java
│     │  │              └─ jwt
│     │  │                 ├─ JwtAuthFilter.java
│     │  │                 └─ JwtService.java
│     │  └─ resources
│     │     ├─ application.yml
│     │     └─ banner.txt
```

---

## 🔐 Security Design

| Service      | Security Protocol     | Auth Token Type |
|--------------|------------------------|------------------|
| Instapay     | JWT Authentication     | `Authorization: Bearer <token>` |
| CIB Bank     | API Key Authentication | `X-API-KEY: <key>` |
| NBE Bank     | API Key Authentication | `X-API-KEY: <key>` |

---

## 📦 Key Features

- ✅ Microservices for each bank with isolated logic and DB
- 🔁 Distributed Transactions using **Two-Phase Commit Protocol**
- 📡 Secure inter-service calls with **OpenFeign**
- 🔐 API Key security for banks and **JWT** for users
- 🧾 Support for account linking, transfers, transaction history
- 📂 Proper multi-layered structure with controller-service-repository separation
- 💡 Designed with scalability, security, and code clarity in mind

---

## 🌐 REST API Endpoints

### 🔐 Authentication
| Method | Endpoint                  | Description              |
|--------|---------------------------|--------------------------|
| POST   | `/api/auth/register`      | Register a new user      |
| POST   | `/api/auth/login`         | Authenticate and get JWT |

---

### 👤 User Management
| Method | Endpoint                          | Description                     |
|--------|-----------------------------------|---------------------------------|
| GET    | `/api/users`                      | Retrieve list of all users      |
| GET    | `/api/users/{username}`           | Retrieve user by username       |
| POST   | `/api/users/update_role`          | Update user role (admin only)   |

---

### 🏦 Account Management
| Method | Endpoint                                | Description                             |
|--------|-----------------------------------------|-----------------------------------------|
| GET    | `/api/accounts/`                        | Get all linked accounts for current user|
| GET    | `/api/accounts/{bankName}`              | Get linked accounts for a specific bank |
| POST   | `/api/accounts/`                        | Link a bank account using card & PIN    |
| DELETE | `/api/accounts/`                        | Unlink/delete a bank account            |
| POST   | `/api/accounts/accdetails`              | Get account details by account number   |
| POST   | `/api/accounts/transactions/history`    | Get transaction history for account     |

---

### 💳 Transactions
| Method | Endpoint                         | Description                                      |
|--------|----------------------------------|--------------------------------------------------|
| POST   | `/api/transactions/send`         | Send money between accounts via 2PC             |
| POST   | `/api/transactions/history`      | Retrieve history of past transactions           |

---

## 🔧 Tech Stack

| Layer              | Technology                          |
|--------------------|--------------------------------------|
| Language           | Java 17                              |
| Framework          | Spring Boot 3                        |
| Security           | Spring Security, JWT, API Keys       |
| Communication      | OpenFeign (REST Client)              |
| Persistence        | Spring Data JPA, MySQL               |
| Build Tool         | Maven                                |
| Testing            | JUnit, Postman                       |
| Auth Standards     | Bearer Token (JWT), API Key Filter   |

---

## 🔄 How the 2PC Transaction Works

Instapay ensures transaction **atomicity** using Two-Phase Commit:

1. **Prepare Phase:**
   - Instapay sends a debit request to the source bank (e.g., CIB).
   - If debit succeeds, sends credit request to destination bank (e.g., NBE).

2. **Commit Phase:**
   - If both banks respond with success → Commit transaction ✅
   - If any failure occurs → Rollback entire operation ❌

This ensures **no partial transfers** ever occur.

---

## 🚀 Running the Project Locally

1. 📥 Clone the repository
2. 📦 Install MySQL and create 3 databases:
   - `instapay_db`
   - `cib_bank_db`
   - `nbe_bank_db`
3. 🛠 Configure `application.yml` for each service with DB credentials and ports
4. ▶ Start services in this order:
   - `cib-bank`
   - `nbe-bank`
   - `instapay`
5. 📬 Use Postman to interact with endpoints (login, add banks, transfer money, etc.)

---

## 👥 Contributing

This project is ideal for developers studying distributed systems, banking APIs, or Spring microservices.  
You're welcome to fork, clone, star ⭐, or contribute through pull requests.

---

## 📜 License

This project is provided for educational and demonstration purposes.  
Please use responsibly and with proper attribution if reused.

---
