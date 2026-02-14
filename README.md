# bankingapp

A structured learning project for transitioning from QA Automation Engineering into Java Backend Engineering. Built with Java 17 and Spring Boot 3.x, this repository documents hands-on practice with core backend concepts through incremental, well-organized development.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [How to Run](#how-to-run)
- [Available Endpoints](#available-endpoints)
- [Concepts Practiced](#concepts-practiced)
- [Planned Enhancements](#planned-enhancements)
- [Learning Objectives](#learning-objectives)
- [Author](#author)

---

## Project Overview

This project serves as a progressive backend sandbox — starting from the fundamentals of Spring Boot and building toward production-relevant patterns. Each iteration introduces a new concept, making the commit history itself a learning artifact.

The project currently exposes a basic REST API with a layered architecture and will expand to cover persistence, security, testing, and more.

---

## Architecture

The project follows a standard **Layered Architecture** pattern:

```
Client Request
      │
      ▼
┌─────────────┐
│  Controller │  ← Handles HTTP requests, delegates to service
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   Service   │  ← Contains business logic
└──────┬──────┘
       │
       ▼
┌─────────────┐
│     DTO     │  ← Shapes data returned to the client
└─────────────┘
```

**Layer Responsibilities:**

- **Controller** — Receives HTTP requests, validates input, and returns HTTP responses. No business logic lives here.
- **Service** — Encapsulates all business logic. Keeps controllers thin and testable.
- **DTO (Data Transfer Object)** — Decouples internal domain models from the API response contract, giving control over what is exposed.

---

## Tech Stack

| Technology      | Version | Purpose                          |
|-----------------|---------|----------------------------------|
| Java            | 17      | Core language (LTS release)      |
| Spring Boot     | 3.x     | Application framework            |
| Spring Web MVC  | —       | REST API support                 |
| Maven           | 3.x     | Build and dependency management  |
| Jackson         | —       | JSON serialization               |

---

## How to Run

### Prerequisites

- Java 17+ installed
- Maven 3.x installed

### Steps

**1. Clone the repository**

```bash
git clone https://github.com/ak-admi/bankingapp.git
cd bankingapp
```

**2. Build the project**

```bash
mvn clean install
```

**3. Run the application**

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080` by default.

---

## Available Endpoints

### Base URL: `http://localhost:8080`

| Method | Endpoint  | Description              | Response          |
|--------|-----------|--------------------------|-------------------|
| GET    | `/hello`  | Returns a greeting message | `200 OK` — JSON  |

### Sample Response — `GET /hello`

```json
{
  "message": "Hello, World!",
  "status": "success"
}
```

---

## Concepts Practiced

- [x] Spring Boot project structure and auto-configuration
- [x] REST Controller creation with `@RestController` and `@GetMapping`
- [x] Layered architecture separation (Controller → Service → DTO)
- [x] JSON response shaping using DTOs
- [x] Spring MVC request-response lifecycle

---

## Planned Enhancements

The following topics are queued for upcoming iterations:

- [ ] Integrate Spring Data JPA with an H2 in-memory database
- [ ] Add full CRUD endpoints for a banking domain entity (e.g. Account, Transaction)
- [ ] Implement global exception handling with `@ControllerAdvice`
- [ ] Add input validation using Bean Validation (`@Valid`, `@NotBlank`, etc.)
- [ ] Write unit tests with JUnit 5 and Mockito
- [ ] Write integration tests using `@SpringBootTest`
- [ ] Introduce Spring Security for basic authentication
- [ ] Add API documentation with SpringDoc OpenAPI (Swagger UI)
- [ ] Dockerize the application

---

## Learning Objectives

This project is intentionally scoped to reinforce the following goals:

1. **Build backend intuition from a QA foundation** — applying test thinking to code structure and API design.
2. **Understand the Spring ecosystem** — not just how to use it, but why its patterns exist.
3. **Write production-relevant code** — avoiding "tutorial code" by focusing on separation of concerns from day one.
4. **Document the learning process** — treating this repository as a portfolio artifact that demonstrates growth over time.

---

## Author

**ak-admi**
QA Automation Engineer → Java Backend Engineer

- GitHub: [@ak-admi](https://github.com/ak-admi)

---

*This project is part of a structured backend engineering study plan. Contributions and feedback are welcome.*
