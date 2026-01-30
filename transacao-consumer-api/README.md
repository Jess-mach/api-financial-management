### **Technical Documentation: `transacao-consumer-api`**

### **1. Architecture**

The `transacao-consumer-api` is a Spring Boot-based microservice designed to consume and process financial transactions. It follows a layered architecture:

*   **Presentation Layer:** A RESTful API built with Spring Web MVC, secured using JWT. OpenAPI documentation is provided through Springdoc.
*   **Business Logic Layer:** Contains the core application logic for validating and processing transactions.
*   **Data Access Layer:** Uses Spring Data JPA to interact with a PostgreSQL database.
*   **Messaging:** Integrates with Apache Kafka to consume transaction messages.

### **2. Functionalities**

*   **Transaction Consumption:** Consumes transaction messages from a Kafka topic.
*   **Transaction Validation:** Implements business rules to validate incoming transactions.
*   **Transaction Persistence:** Stores validated transactions in a PostgreSQL database.
*   **REST API:** Exposes endpoints for monitoring and managing transactions.
*   **Security:** Secures API endpoints using JWT-based authentication.

### **3. Configuration**

*   **Database:**
    *   **URL:** `jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5433}/${DB_NAME:transacoes_db}`
    *   **Username:** `${DATASOURCE_USERNAME:postgres}`
    *   **Password:** `${DATASOURCE_PASSWORD:postgres}`
*   **Kafka:**
    *   Configuration is expected to be provided through environment variables or a separate configuration file, as it is not present in `application.properties`.
*   **JWT Secret:**
    *   The secret for signing JWT tokens is configured via the `api.security.token.secret` property, with a default value provided.
*   **Containerization:**
    *   The application is containerized using Docker. The `Dockerfile` defines a multi-stage build that first builds the application using Maven and then creates a lightweight final image with the JRE and the application JAR. The container exposes port 8080.

### **4. Dependencies**

*   **Spring Boot:** Core framework for building the application.
*   **Spring Web:** For creating the RESTful API.
*   **Spring Data JPA:** For database interaction.
*   **PostgreSQL Driver:** To connect to the PostgreSQL database.
*   **Spring Kafka:** For Kafka integration.
*   **Lombok:** To reduce boilerplate code.
*   **Springdoc OpenAPI:** For API documentation.
*   **java-jwt:** For JWT-based authentication.
*   **common-transacao-lib:** A shared library containing common transaction-related classes.



´´´bash

DATASOURCE_PASSWORD=db_password DATASOURCE_URL=jdbc:postgresql://localhost:5433/transacoes_db DATASOURCE_USERNAME=db_user JWT_SECRET=12345678 mvn spring-boot:run


´´´

´´´shell
docker exec -it kafka kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic TRANSACAO-TOPIC
´´´

mvn clean install


http://localhost:8082/swagger-ui/index.html