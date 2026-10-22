# Product Service

A simple REST API built with Java and Spring Boot to manage products and their categories.

You can create, view, update and delete products, and see which products belong to each category. All data is stored in a MySQL database.

I built this project to learn backend development with Spring Boot and to practice writing clean, layered code.

---

## What It Does

- Add, view, update and delete products
- View categories along with the products inside them
- Every product belongs to one category
- Uses DTOs, so the API response is separate from the database entity
- Interactive API docs with Swagger, so you can test endpoints in the browser

---

## Tech Stack

| Tool | Used For |
|---|---|
| Java 17 | Main language |
| Spring Boot 3 | Application framework |
| Spring Web | Building REST APIs |
| Spring Data JPA | Talking to the database |
| MySQL | Database |
| Swagger / OpenAPI | API documentation |
| Lombok | Less boilerplate code |
| Maven | Build and dependency management |

---

## Before You Start

Make sure these are installed on your computer:

- JDK 17 or newer
- Maven 3 or newer
- MySQL Server
- Git

---

## How to Run

**1. Clone the project**

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd <YOUR_PROJECT_DIRECTORY>
```

**2. Create the database**

Open MySQL and run:

```sql
CREATE DATABASE product_service;
```

**3. Set your database details**

Open `src/main/resources/application.properties` and check these lines:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/product_service
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
```

Then set your MySQL username and password as environment variables:

```bash
# Mac / Linux
export DB_USERNAME=your_username
export DB_PASSWORD=your_password

# Windows (Command Prompt)
set DB_USERNAME=your_username
set DB_PASSWORD=your_password
```

> Never upload your real password or any secret key to GitHub.

**4. Build the project**

```bash
mvn clean install
```

**5. Start the application**

```bash
mvn spring-boot:run
```

You can also run the main class directly from your IDE.

The app starts on port `8080` by default.

---

## Try the API

After the app starts, open Swagger in your browser:

```
http://localhost:8080/swagger-ui/index.html
```

From there you can see every endpoint and test it without any extra tool. You can also use Postman if you prefer.

### Main Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/products` | Create a new product |
| GET | `/products` | Get all products |
| GET | `/products/{id}` | Get one product |
| PUT | `/products/{id}` | Update a product |
| DELETE | `/products/{id}` | Delete a product |
| GET | `/categories` | Get all categories |
| GET | `/categories/{id}` | Get one category with its products |

*Exact paths may differ slightly. Swagger always shows the latest list.*

---

## Project Structure

```
product-service/
├── src/
│   ├── main/
│   │   ├── java/.../
│   │   │   ├── controller/    # Handles HTTP requests
│   │   │   ├── service/       # Business logic
│   │   │   ├── repository/    # Database access
│   │   │   ├── entity/        # Database tables as Java classes
│   │   │   ├── dto/           # Data sent and received by the API
│   │   │   └── mapper/        # Converts entity to DTO and back
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

The code follows a simple flow: a request comes into the **controller**, the **service** handles the logic, and the **repository** reads or writes the data.

---

## What I Learned

- Building REST APIs with Spring Boot
- Writing CRUD operations
- Connecting MySQL using Spring Data JPA
- Working with entities, repositories and DTOs
- Keeping code organised in layers
- Testing and documenting APIs with Swagger
- Managing a project with Maven

---

## Future Plans

- Add input validation and proper error handling
- Add login and roles using Spring Security
- Write unit and integration tests
- Add pagination, sorting and filtering
- Run the app with Docker
- Set up CI/CD for automatic builds and tests

---

## Author

**Amrit Raj Singh**
B.Tech in Computer Science and Information Technology


---

This project was made for learning and practice. Feedback and suggestions are always welcome.