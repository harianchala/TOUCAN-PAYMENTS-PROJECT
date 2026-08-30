# TOUCAN – Transaction Service

## 1. Understanding of the Problem

The objective of this project is to build a Spring Boot transaction service that can create and manage transactions. Each transaction contains a transaction ID, customer ID, amount, currency, transaction type, and transaction status.

I implemented the application using an MVC-based structure with Entity, Repository, Service, Controller, and Exception Handling layers.

The main operations implemented are:

* Create a transaction
* Get a transaction by transaction ID
* Update transaction status
* Get all transactions for a customer

New transactions are created with `PENDING` status. A transaction status can only be changed while the current status is `PENDING`.

## 2. Architecture

The application follows a layered MVC-style architecture:

- Entity — Represents transaction data and enums.
- Repository — Handles persistence using Spring Data JPA and H2.
- Service — Contains transaction business logic and status transition rules.
- Controller — Exposes REST APIs.
- Exception Handling — Handles custom exceptions and returns appropriate HTTP responses.

## 3. Assumptions

* Transaction ID is unique and is used as the primary key.
* Transaction IDs and customer IDs are represented as `String` because they can contain both letters and numbers.
* `BigDecimal` is used for transaction amounts because it is appropriate for precise financial values.
* Currency is represented as a `String`.
* Transaction type is restricted to `PAYMENT`, `TRANSFER`, and `REFUND`.
* Transaction status is restricted to `PENDING`, `COMPLETED`, `FAILED`, and `CANCELLED`.
* A transaction that is no longer `PENDING` cannot have its status changed.

## 4. Validation Rules

The following validation rules are implemented:

* `@NotBlank` — transaction ID, customer ID, and currency cannot be empty or blank.
* `@Positive` — transaction amount must be greater than zero.
* `@NotNull` — transaction type and transaction status cannot be null.
* `@Valid` is used in the controller to trigger request validation.
* ENUMS restrict transaction type and status to their predefined values.

## 5. API Endpoints


*  `POST ` — `/transaction/createTransaction` — Create a new transaction 
*  `GET`  — `/transaction/{transactionId}` — Get a transaction by ID 
*  `PATCH` — `/transaction/{transactionId}/status` — Update the status of a transaction 
*  `GET`  — `/transaction/customer/{customerId}` — Get all transactions for a customer 

`@RequestBody` is used to convert JSON request data into Java objects using Jackson. `@PathVariable` is used for transaction/customer IDs, and `@RequestParam` is used when updating the transaction status.

## 6. Testing Approach

I used JUnit 5 and Mockito for automated testing.

The tests cover:

1. Successful transaction creation.
2. Invalid transaction rejected by validation.
3. Duplicate transaction ID rejected.
4. Transaction not found.
5. Successful transaction status update.
6. Successful transaction retrieval.

Mockito is used to mock the repository when testing service-layer business logic. `MockMvc` is used to verify that invalid API input is rejected with `400 Bad Request`.

## 7. Known Limitations

* DTOs are not used; the entity is directly used for JSON request/response mapping.
* Authentication and authorization are not implemented because they were outside the provided requirements.
* Lombok was not used in the current implementation. Getters, setters, and constructors were written explicitly. Lombok could be introduced later to reduce boilerplate code.

## 8. Improvements With More Time

With more time, I would:

* Introduce DTOs to separate API models from persistence entities.
* Add authentication and authorization using spring security and JWT
* Use Lombok to reduce repetitive getter, setter, and constructor code where appropriate.
* Add more detailed validation and field-level error responses.
* Improve the error response structure with additional details such as timestamp, path, and validation errors using e.printStackTrace().

## 9.Test Run Output

The complete test suite was executed using Maven.

```text
[INFO] Running com.example.transactionstarter.TransactionControllerTest

[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

[INFO] Running com.example.transactionstarter.TransactionServiceTest

[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0

[INFO] Results:

[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 7.717 s
[INFO] Finished at: 2026-08-30T18:47:15+05:30
[INFO] ------------------------------------------------------------------------
