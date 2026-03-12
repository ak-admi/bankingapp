# Banking App — Spring Boot Learning Project

A personal sandbox project built with **Spring Boot 3.5** to explore and experiment with advanced backend concepts including transactional behaviour, optimistic concurrency control, self-invocation proxy issues, and atomic SQL updates.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5.x |
| Persistence | Spring Data JPA / Hibernate |
| Database | H2 (file-based, persistent) |
| Build Tool | Maven |
| Utilities | Lombok |

---

## Project Structure

```
src/main/java/com/alok/bankingapp/
├── BankingappApplication.java          # Spring Boot entry point
├── controller/
│   ├── HelloController.java
│   ├── UserController.java             # User CRUD + transaction demo endpoints
│   └── InventoryController.java        # Inventory buy / decrement endpoints
├── service/
│   ├── UserService.java                # Core user business logic
│   ├── TransactionDemoService.java     # @Transactional rollback & self-invocation demos
│   ├── InventoryService.java           # Optimistic locking + retry logic
│   └── HelloService.java
├── entity/
│   ├── User.java                       # JPA entity with NOT NULL / UNIQUE constraints
│   └── Inventory.java                  # JPA entity with @Version for optimistic locking
├── repository/
│   ├── UserRepository.java
│   └── InventoryRepository.java        # Custom atomic decreaseStock query
├── dto/
│   ├── UserRequest.java
│   ├── UserResponse.java
│   └── ErrorResponse.java
└── exception/
    ├── GlobalExceptionHandler.java     # @RestControllerAdvice centralised error handling
    ├── UserNotFoundException.java
    ├── InventoryNotFoundException.java
    ├── InsufficientStockException.java
    └── ConcurrencyException.java
```

---

## Key Concepts Explored

### 1. `@Transactional` Rollback
The `POST /users/fail` endpoint demonstrates that when a `RuntimeException` is thrown inside a `@Transactional` method after a save, the entire transaction is rolled back — the user is **not** persisted to the database.

### 2. Spring Proxy & Self-Invocation Problem
The `POST /users/self` endpoint demonstrates a classic Spring AOP trap: when an outer method calls an inner `@Transactional` method **on the same class** without going through the Spring proxy, the transaction annotation is silently ignored. The `InventoryService` resolves this by injecting itself with `@Lazy` so that internal calls go through the proxy correctly.

### 3. Optimistic Concurrency Control (`@Version`)
The `Inventory` entity uses a `@Version` field managed by Hibernate. On concurrent updates, Hibernate throws `ObjectOptimisticLockingFailureException` instead of overwriting data silently.

### 4. Retry Logic with Backoff
`InventoryService.pruchaseItem()` wraps the internal transactional purchase in a retry loop (up to 3 attempts with a 50 ms backoff) to gracefully handle concurrent update conflicts before returning a `503 SERVICE_UNAVAILABLE` to the caller.

### 5. Atomic SQL Update
`POST /inventory/{id}/buy` uses a custom JPQL query (`repository.decreaseStock(id)`) that performs a single atomic `UPDATE ... WHERE stock > 0` to avoid race conditions without relying on application-level locking.

### 6. Global Exception Handling
`GlobalExceptionHandler` uses `@RestControllerAdvice` to map domain exceptions to HTTP status codes consistently across all controllers.

---

## API Reference

### User Endpoints — `/users`

| Method | Path | Description |
|---|---|---|
| `POST` | `/users` | Create a new user |
| `GET` | `/users` | Get all users |
| `GET` | `/users/{id}` | Get user by ID |
| `PUT` | `/users/{id}` | Update a user |
| `DELETE` | `/users/{id}` | Delete a user |
| `POST` | `/users/fail` | Demo: `@Transactional` rollback on exception |
| `POST` | `/users/self` | Demo: self-invocation proxy bypass (no rollback) |

#### Sample Request Body (create / update)
```json
{
  "name": "Alok",
  "email": "alok@example.com"
}
```

### Inventory Endpoints — `/inventory`

| Method | Path | Description |
|---|---|---|
| `POST` | `/inventory/{id}/buy` | Atomic stock decrement (SQL-level) |
| `POST` | `/inventory/{id}/decrement?quantity=N` | Decrement by quantity with optimistic lock + retry |

---

## Running the Application

### Prerequisites
- Java 17+
- Maven 3.6+

### Steps

```bash
# Clone the repository
git clone https://github.com/ak-admi/bankingapp.git
cd bankingapp

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The application starts on **`http://localhost:8080`** by default.

---

## H2 Console

Since this project uses an H2 file-based database, you can inspect the data at runtime via the browser console:

```
URL:      http://localhost:8080/h2-console
JDBC URL: jdbc:h2:file:./data/testdb;AUTO_SERVER=TRUE
Username: sa
Password: (leave blank)
```

---

## Database Configuration

Configured in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:h2:file:./data/testdb;AUTO_SERVER=TRUE
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.h2.console.enabled=true
```

Schema is auto-managed by Hibernate on startup (`ddl-auto=update`), and SQL queries are printed to the console for easy debugging.

---

## Error Responses

All errors follow a consistent JSON structure:

```json
{
  "code": "INSUFFICIENT_STOCK",
  "message": "Requested 5 but only 2 available"
}
```

| Exception | HTTP Status |
|---|---|
| `UserNotFoundException` | `404 Not Found` |
| `InventoryNotFoundException` | `404 Not Found` |
| `InsufficientStockException` | `409 Conflict` |
| `ConcurrencyException` | `503 Service Unavailable` |
| `StaleObjectStateException` | `409 Conflict` |

---

## Author

**Alok** — Automation QA Engineer exploring Spring Boot internals through hands-on experimentation.
