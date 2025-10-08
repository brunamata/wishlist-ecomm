# E-commerce Wishlist API

A standalone microservice for managing customer wishlists in an e-commerce platform.

## About The Project

This project is a RESTful API that provides all the necessary operations for managing a customer's wishlist. It is designed following **Clean Architecture** principles to ensure a clear separation of concerns, making the codebase scalable, maintainable, and highly testable.

The core business logic is completely decoupled from the web and persistence layers. The API is built to be robust, with comprehensive validation, exception handling, and a full suite of unit, integration, and BDD tests.

### Key Features

* Add a product to a customer's wishlist.
* Remove a product from the wishlist.
* List all products in a customer's wishlist.
* Check if a specific product exists in the wishlist.

-----

## Built With

This project uses a modern, industry-standard tech stack:

* **[Spring Boot 3](https://spring.io/projects/spring-boot)**
* **[Java 21](https://www.oracle.com/java/)**
* **[MongoDB](https://www.mongodb.com/)**
* **[Docker & Docker Compose](https://www.docker.com/)**
* **[Maven](https://maven.apache.org/)**
* **[JUnit 5,](https://junit.org/junit5/) [Mockito](https://site.mockito.org/) [& Cucumber (BDD)](https://cucumber.io/)**
* **[Testcontainers](https://www.testcontainers.org/)**
* **[Springdoc OpenAPI (Swagger)](https://springdoc.org/)**

-----

## Getting Started

To get a local copy up and running, follow these steps.

### Prerequisites

* **JDK 21** or later (Amazon Corretto is recommended).
* **Docker** and **Docker Compose**.
* **Apache Maven** 3.8 or later.
* An IDE like IntelliJ IDEA or VS Code.

### Running the Application

There are two primary ways to run the application:

#### 1\. For Development (IDE + Docker for Database)

This is the recommended approach for active development and debugging.

1.  **Start the database container:**
    This command starts only the MongoDB service defined in the `docker-compose.yml` file.

    ```bash
    docker-compose up mongo-db -d
    ```

2.  **Run the application:**
    You can run the main application class `WishlistApplication.java` directly from your IDE.

    Alternatively, you can use the Maven command:

    ```bash
    mvn spring-boot:run
    ```

#### 2\. As a Full Docker Deployment

This method builds a Docker image for the application and runs both the API and the database as containers, simulating a production-like environment.

1.  **Build the application JAR:**
    Ensure you have an up-to-date JAR file by running the Maven build.

    ```bash
    mvn clean package
    ```

2.  **Build and run the containers:**
    This command will build the `wishlist-api` image and start all services. 

    ```bash
    docker-compose up --build
    ```

The API will be available at `http://localhost:8080`.

-----

## Testing

The project has a comprehensive test suite.

* **Run all tests (Unit, Integration, and BDD):**
  This command runs the full test suite.

  ```bash
  mvn clean verify
  ```

* **BDD Feature Files:**
  The application's behavior is described in Gherkin syntax in `.feature` files located under `src/test/resources/features/`.

-----

## API Reference

### Interactive Documentation (Swagger UI)

The API is fully documented using OpenAPI 3. You can access the interactive Swagger UI to view all endpoints and test them directly from your browser.

* **URL:** [**http://localhost:8080/swagger-ui.html**](https://www.google.com/search?q=http://localhost:8080/swagger-ui.html)

### Example `curl` Commands

Here are some examples of how to interact with the API using `curl`.

#### Add a product to a wishlist

```bash
curl --request POST \
  --url http://localhost:8080/api/wishlist/user1/items \
  --header 'content-type: application/json' \
  --data '{
  "itemId": "tenis1"
}'
```

#### Get all products in a wishlist

```bash
curl --request GET \
  --url http://localhost:8080/api/wishlist/user1
```

#### Check if a product exists in a wishlist

```bash
curl --request GET \
  --url http://localhost:8080/api/wishlist/user1/items/tenis1
```

#### Remove a product from a wishlist

```bash
curl --request DELETE \
  --url http://localhost:8080/api/wishlist/user1/items/tenis1
```