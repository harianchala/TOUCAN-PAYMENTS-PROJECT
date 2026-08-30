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

## 2. Assumptions

* Transaction ID is unique and is used as the primary key.
* Transaction IDs and customer IDs are represented as `String` because they can contain both letters and numbers.
* `BigDecimal` is used for transaction amounts because it is appropriate for precise financial values.
* Currency is represented as a `String`.
* Transaction type is restricted to `PAYMENT`, `TRANSFER`, and `REFUND`.
* Transaction status is restricted to `PENDING`, `COMPLETED`, `FAILED`, and `CANCELLED`.
* A transaction that is no longer `PENDING` cannot have its status changed.

## 3. Validation Rules

The following validation rules are implemented:

* `@NotBlank` — transaction ID, customer ID, and currency cannot be empty or blank.
* `@Positive` — transaction amount must be greater than zero.
* `@NotNull` — transaction type and transaction status cannot be null.
* `@Valid` is used in the controller to trigger request validation.
* ENUMS restrict transaction type and status to their predefined values.

## 4. API Endpoints

| Method | Endpoint                              | Purpose                             |
| ------ | ------------------------------------- | ----------------------------------- |
| POST   | `/transaction/createTransaction`      | Create a new transaction            |
| GET    | `/transaction/{transactionId}`        | Get a transaction by ID             |
| PATCH  | `/transaction/{transactionId}/status` | Update the status of a transaction  |
| GET    | `/transaction/customer/{customerId}`  | Get all transactions for a customer |

`@RequestBody` is used to convert JSON request data into Java objects using Jackson. `@PathVariable` is used for transaction/customer IDs, and `@RequestParam` is used when updating the transaction status.

## 5. Testing Approach

I used JUnit 5 and Mockito for automated testing.

The tests cover:

1. Successful transaction creation.
2. Invalid transaction rejected by validation.
3. Duplicate transaction ID rejected.
4. Transaction not found.
5. Successful transaction status update.
6. Successful transaction retrieval.

Mockito is used to mock the repository when testing service-layer business logic. `MockMvc` is used to verify that invalid API input is rejected with `400 Bad Request`.

## 6. Known Limitations

* DTOs are not used; the entity is directly used for JSON request/response mapping.
* Authentication and authorization are not implemented because they were outside the provided requirements.
* Lombok was not used in the current implementation. Getters, setters, and constructors were written explicitly. Lombok could be introduced later to reduce boilerplate code.

## 7. Improvements With More Time

With more time, I would:

* Introduce DTOs to separate API models from persistence entities.
* Add authentication and authorization.
* Use Lombok to reduce repetitive getter, setter, and constructor code where appropriate.
* Add more detailed validation and field-level error responses.
* Improve the error response structure with additional details such as timestamp, path, and validation errors.

## 8. Assigned Variant

The application was implemented according to the assigned variant provided in the invitation. No additional assumptions were made about the variant beyond the requirements provided for this candidate.

# AI Usage Disclosure

I used **ChatGPT** as an AI coding assistant during the development of this project.

I mainly used AI for:

* Creating and understanding the `GlobalExceptionHandler` and custom exception handling.
* Getting guidance on writing JUnit 5 and Mockito test cases.
* Troubleshooting compile-time and runtime errors encountered during development.
* Troubleshooting the port `8080` conflict when starting the Spring Boot application.

The significant code suggested by AI was related to the `GlobalExceptionHandler` and testing structure. I reviewed and adapted the suggestions to match my existing project structure, packages, classes, and business logic.

Some AI suggestions did not directly match my project configuration, so I checked the actual compiler and runtime error messages and corrected the package names, imports, and configuration where necessary.

I verified the final implementation by running the application, testing the API endpoints, checking validation and exception handling, and running the JUnit test suite successfully. I understand the code submitted and can explain or modify it during the live session.
