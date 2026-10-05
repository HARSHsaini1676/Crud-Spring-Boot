# CrudSpringBoot

A RESTful CRUD API for managing students, built with Spring Boot and PostgreSQL.

## Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java 26 |
| Framework | Spring Boot 4.1.0 |
| Persistence | Spring Data JPA + Hibernate |
| Database | PostgreSQL |
| Validation | Jakarta Bean Validation |
| Build | Maven |

## Prerequisites

- Java 26+
- Maven 3.9+
- PostgreSQL running locally (or reachable via env vars)

## Getting Started

### 1. Set up the database

Create a PostgreSQL database:

```sql
CREATE DATABASE "SpringCrud";
```

### 2. Configure environment variables

Copy the example properties file and fill in your values:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Then set the following environment variables (or edit the fallback values in `application.properties` for local dev only):

| Variable | Description | Example |
|----------|-------------|---------|
| `DB_URL` | JDBC connection URL | `jdbc:postgresql://localhost:5432/SpringCrud` |
| `DB_USERNAME` | Database username | `postgres` |
| `DB_PASSWORD` | Database password | `yourpassword` |

### 3. Run the application

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

## API Reference

Base path: `/api/students`

### Create a student
```
POST /api/students
Content-Type: application/json

{
  "name": "Harsh",
  "age": 20,
  "email": "harsh@example.com",
  "rollNo": 101,
  "subject": "Computer Science"
}
```
Returns `201 Created` with the created student.

### Get a student by ID
```
GET /api/students/{id}
```
Returns `200 OK` with the student, or `404 Not Found`.

### Get all students
```
GET /api/students
```
Returns `200 OK` with a list of active students, or `404 Not Found` if none exist.

### Update a student
```
PUT /api/students?id={id}
Content-Type: application/json

{
  "name": "Harsh Updated",
  "age": 21,
  "rollNo": 102,
  "subject": "Data Science"
}
```
Returns `200 OK` with the updated student.

### Hard delete a student
```
DELETE /api/students?id={id}
```
Returns `204 No Content`. Permanently removes the record.

### Soft delete a student
```
PATCH /api/students/delete-soft?id={id}
```
Returns `204 No Content`. Marks the student as deleted without removing the database row. Soft-deleted students are excluded from all read operations, and their email becomes available for re-registration.

## Error Responses

All errors follow a consistent JSON shape:

```json
{
  "timestamp": "2026-10-05T10:00:00",
  "statusCode": 404,
  "error": "Not Found",
  "message": "Resource with id 1 Not Found",
  "path": "/api/students/1"
}
```

Validation errors (`400`) include a `fieldErrors` map:

```json
{
  "timestamp": "2026-10-05T10:00:00",
  "statusCode": 400,
  "error": "Bad Request",
  "message": "Validation Failed",
  "path": "/api/students",
  "fieldErrors": {
    "email": "Student email must be valid",
    "age": "Student must be atleast 18 years old"
  }
}
```

## Project Structure

```
src/main/java/com/harsh/crudspringboot/
├── controller/         StudentController.java
├── service/            StudentService.java
├── repository/         StudentRepository.java
├── entity/             Student.java
├── dto/                Request/response and exception DTOs
└── exception/          Custom exceptions + GlobalExceptionHandler
```
