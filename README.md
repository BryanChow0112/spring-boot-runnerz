
# Runnerz

Runnerz is a simple web application built using Spring Boot 3, created to apply my knowledge and demonstrate various technologies and best practices for developing enterprise-grade Java applications. The project includes a REST API that communicates with a database, comprehensive testing and several advanced features.


## Badges

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)


## Technologies Used

- Spring Boot 3: Main framework for building the application.
- Spring MVC: For building RESTful web services.
- Spring Data JPA: For database interactions.
- PostgreSQL: Primary database used in production.
- H2 Database: In-memory database for testing purposes.
- Docker Compose: For containerizing the PostgreSQL database.
- JUnit 5: For unit testing.
- Mockito: For mocking dependencies in tests.
- Jackson: For JSON processing.
- RestTemplate and WebClient: For consuming RESTful web services.
- Postman: For testing and interacting with the RESTful API.


## API Endpoints

- GET /api/runs: Retrieve all runs.
- GET /api/runs/{id}: Retrieve a specific run by ID.
- POST /api/runs: Create a new run.
- PUT /api/runs/{id}: Update an existing run.
- DELETE /api/runs/{id}: Delete a run.


## Useful Resources

- [Spring Initializr](https://start.spring.io/)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/)
