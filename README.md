# Product Service

A REST API built with Java and Spring Boot for managing products and their categories. Read access is open to everyone, while creating, updating and deleting data is protected with JWT authentication and role-based access control, using Keycloak as the identity provider.

I built this project to practise backend development the way it is done in real teams: a layered structure, DTOs instead of exposing entities, centralised error handling, API documentation, and token-based security.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Data Model](#data-model)
- [Security and Roles](#security-and-roles)
- [API Endpoints](#api-endpoints)
- [Example Requests](#example-requests)
- [Error Handling](#error-handling)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Actuator and Monitoring](#actuator-and-monitoring)
- [Project Structure](#project-structure)
- [Roadmap](#roadmap)
- [Author](#author)

---

## Features

- Full CRUD for products
- Create, read and delete for categories
- Every product belongs to exactly one category, and a category returns the list of its products
- DTO layer and mapper classes, so the API contract is separate from the database entities
- Stateless authentication: the service validates JWTs issued by Keycloak (OAuth2 Resource Server)
- Role-based authorisation with `@PreAuthorize` (`ADMIN` and `SELLER`)
- Global exception handling with a consistent JSON error response
- Interactive API documentation with Swagger UI (springdoc-openapi)
- Spring Boot Actuator with a custom endpoint
- Request and application logging with SLF4J (Lombok `@Slf4j`)

---

## Tech Stack

| Area | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.1.0 |
| Web | Spring Web MVC |
| Persistence | Spring Data JPA, Hibernate |
| Database | MySQL |
| Security | Spring Security, OAuth2 Resource Server |
| Identity Provider | Keycloak |
| API Docs | springdoc-openapi 3.1.0 (Swagger UI) |
| Monitoring | Spring Boot Actuator |
| Boilerplate | Lombok |
| Build Tool | Maven |

---

## Architecture

The project follows a classic layered design. A request travels down through the layers, and the response travels back up.

```
Client
  |
  v
Security filter chain  (validates the JWT, maps Keycloak roles to authorities)
  |
  v
Controller             (HTTP layer, request mapping, role checks)
  |
  v
Service                (business logic and validation rules)
  |
  v
Repository             (Spring Data JPA)
  |
  v
MySQL
```

- **Controllers** only deal with HTTP: paths, status codes and request/response bodies.
- **Services** hold the business rules, such as checking that a category name is unique or that a category exists before a product is created.
- **Mappers** convert between entities and DTOs, so entities are never returned directly to the client.
- **GlobalExceptionHandler** turns custom exceptions into clean JSON error responses.

---

## Data Model

There are two main tables, connected by a one-to-many relationship.

```
Category (1) ------------< (many) Product
```

**Category**

| Field | Type | Notes |
|---|---|---|
| id | Long | Primary key, auto-generated |
| name | String | Must be unique (checked in the service layer) |
| products | List of Product | One-to-many, cascade `ALL` |

**Product**

| Field | Type | Notes |
|---|---|---|
| id | Long | Primary key, auto-generated |
| name | String | |
| description | String | |
| price | Double | |
| category | Category | Many-to-one, `category_id` is required (not null) |

Because the relationship uses `CascadeType.ALL`, deleting a category also deletes all products that belong to it.

---

## Security and Roles

This service does not issue tokens or store passwords. Users log in with **Keycloak**, and Keycloak issues a signed JWT. This service only **validates** that token on every protected request using the Keycloak public keys (JWK set).

How the role mapping works:

1. The client sends the token in the header: `Authorization: Bearer <token>`.
2. Spring Security validates the token signature against the Keycloak JWK set URI.
3. A custom converter (`KeyClockRoleReader`) reads the roles from the `realm_access.roles` claim of the token and adds the `ROLE_` prefix. For example, the Keycloak realm role `SELLER` becomes the authority `ROLE_SELLER`.
4. `@PreAuthorize` on the controller methods checks those authorities.

**Access rules**

| Operation | Who can do it |
|---|---|
| Any `GET` request | Everyone (no token needed) |
| Create or delete a category | `ADMIN` |
| Create, update or delete a product | `SELLER` |
| Any other request | Any authenticated user (token required) |

To test the protected endpoints you need a Keycloak realm with the roles `ADMIN` and `SELLER` created, and users assigned to them. See [Getting Started](#getting-started).

---

## API Endpoints

Base URL: `http://localhost:9090`

### Categories

| Method | Endpoint | Description | Access |
|---|---|---|---|
| GET | `/api/categories` | Get all categories with their products | Public |
| GET | `/api/categories/{id}` | Get one category with its products | Public |
| POST | `/api/categories` | Create a category | `ADMIN` |
| DELETE | `/api/categories/{id}` | Delete a category (and its products) | `ADMIN` |

### Products

| Method | Endpoint | Description | Access |
|---|---|---|---|
| GET | `/api/products` | Get all products | Public |
| GET | `/api/products/{id}` | Get one product | Public |
| POST | `/api/products` | Create a product | `SELLER` |
| PUT | `/api/products/{id}` | Update a product | `SELLER` |
| DELETE | `/api/products/{id}` | Delete a product | `SELLER` |

---

## Example Requests

### Get an access token from Keycloak

```bash
curl -X POST "http://localhost:8081/realms/master/protocol/openid-connect/token" \
  -d "grant_type=password" \
  -d "client_id=<your-client-id>" \
  -d "username=<your-username>" \
  -d "password=<your-password>"
```

Copy the `access_token` value from the response and use it in the requests below.

### Create a category (ADMIN)

```bash
curl -X POST "http://localhost:9090/api/categories" \
  -H "Authorization: Bearer <access_token>" \
  -H "Content-Type: application/json" \
  -d '{ "name": "Electronics" }'
```

Response `201 Created`:

```json
{
  "id": 1,
  "name": "Electronics",
  "products": []
}
```

### Create a product (SELLER)

```bash
curl -X POST "http://localhost:9090/api/products" \
  -H "Authorization: Bearer <access_token>" \
  -H "Content-Type: application/json" \
  -d '{
        "name": "Wireless Mouse",
        "description": "2.4 GHz wireless mouse",
        "price": 799.0,
        "categoryId": 1
      }'
```

Response `201 Created`:

```json
{
  "id": 1,
  "name": "Wireless Mouse",
  "description": "2.4 GHz wireless mouse",
  "price": 799.0,
  "categoryId": 1
}
```

### Get all categories with products (public)

```bash
curl "http://localhost:9090/api/categories"
```

---

## Error Handling

Errors are handled in one place by `GlobalExceptionHandler`, so every error has the same shape:

```json
{
  "apipath": "uri=/api/categories",
  "statusCode": "CONFLICT",
  "errorMessage": "Category Electronics already exist!",
  "errorTime": "2026-10-22T18:30:15.123456"
}
```

| Situation | HTTP Status |
|---|---|
| Creating a category whose name already exists | `409 Conflict` |
| Creating a product with a category id that does not exist | `404 Not Found` |
| Missing or invalid token | `401 Unauthorized` |
| Valid token but missing the required role | `403 Forbidden` |

---

## Getting Started

### Prerequisites

- JDK 17 or newer
- Maven 3.9 or newer
- MySQL Server 8 or newer
- A running Keycloak server (only needed for the protected endpoints)
- Git

### 1. Clone the repository

```bash
git clone <your-repository-url>
cd products
```

### 2. Create the database

```sql
CREATE DATABASE mydb;
```

The tables are created automatically on first start (`spring.jpa.hibernate.ddl-auto=update`).

### 3. Set up Keycloak

1. Start Keycloak on port `8081`.
2. In your realm, create the realm roles `ADMIN` and `SELLER`.
3. Create users and assign them these roles.
4. Create a client that can request tokens for your users.

The service reads the public keys from:

```
http://localhost:8081/realms/master/protocol/openid-connect/certs
```

If you use a different realm or port, change `jwk-set-uri` in `application.properties`.

### 4. Configure the application

Open `src/main/resources/application.properties` and update the database username and password to match your MySQL setup. See [Configuration](#configuration) for details.

### 5. Build and run

```bash
mvn clean install
mvn spring-boot:run
```

You can also run the `ProductsApplication` class directly from your IDE.

The application starts on **http://localhost:9090**.

---

## Configuration

Main settings in `src/main/resources/application.properties`:

| Property | Purpose | Default in this project |
|---|---|---|
| `server.port` | Port of the application | `9090` |
| `spring.datasource.url` | MySQL connection URL | `jdbc:mysql://localhost:3306/mydb` |
| `spring.datasource.username` | Database user | set to your own |
| `spring.datasource.password` | Database password | set to your own |
| `spring.jpa.hibernate.ddl-auto` | Schema management | `update` |
| `spring.jpa.show-sql` | Print SQL in the console | `true` |
| `spring.security.oauth2.resourceserver.jwt.jwk-set-uri` | Keycloak public keys URL | `http://localhost:8081/realms/master/protocol/openid-connect/certs` |
| `logging.level.com.products` | Log level for this project | `DEBUG` |
| `management.endpoints.web.exposure.include` | Actuator endpoints exposed | `*` |

> **Do not commit real passwords to GitHub.** Keep credentials out of version control by reading them from environment variables, for example:
>
> ```properties
> spring.datasource.username=${DB_USERNAME}
> spring.datasource.password=${DB_PASSWORD}
> ```
>
> and then set `DB_USERNAME` and `DB_PASSWORD` on your machine before starting the app.

---

## API Documentation

Swagger UI is available once the application is running:

```
http://localhost:9090/swagger-ui/index.html
```

The raw OpenAPI document is available at:

```
http://localhost:9090/v3/api-docs
```

Every endpoint is described there with its summary, request body and response codes. To try a protected endpoint from Swagger, you need to send a valid Keycloak token in the `Authorization` header, or use a tool such as Postman.

---

## Actuator and Monitoring

Spring Boot Actuator is enabled. Some useful endpoints:

| Endpoint | Description |
|---|---|
| `/actuator/health` | Application health, with details |
| `/actuator/info` | Application info |
| `/actuator/custom` | A custom endpoint written for this project |

> In this project all actuator endpoints are exposed for learning and local development. In a production environment you should expose only what you need and protect the rest.

---

## Project Structure

```
products/
├── src/
│   ├── main/
│   │   ├── java/com/products/
│   │   │   ├── ProductsApplication.java      # Entry point and OpenAPI info
│   │   │   ├── controller/                   # REST controllers, custom actuator endpoint
│   │   │   ├── service/                      # Business logic
│   │   │   ├── repository/                   # Spring Data JPA repositories
│   │   │   ├── entity/                       # JPA entities (Category, Product, User)
│   │   │   ├── dto/                          # Request, response and error DTOs
│   │   │   ├── mapper/                       # Entity <-> DTO conversion
│   │   │   ├── exception/                    # Custom exceptions and global handler
│   │   │   └── security/                     # Security config and Keycloak role converter
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

---

## Roadmap

Things I plan to improve next:

- Bean validation on request bodies (`@Valid`, `@NotBlank`, `@Positive`)
- A dedicated `ProductNotFoundException` and a catch-all exception handler, so missing resources always return a clean `404`
- Pagination, sorting and filtering for product and category lists
- Unit tests for the service layer and integration tests for the controllers
- Restrict actuator endpoints and move all credentials to environment variables
- Docker and Docker Compose setup for the app, MySQL and Keycloak
- CI pipeline for automatic build and test runs

---

## Author

**Amrit Raj Singh**
B.Tech student, Computer Science and Information Technology
Java Full Stack Developer

This project was built for learning and practice. Feedback and suggestions are always welcome.
