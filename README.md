# Profiler Release 2

## Description

The Project Profiler Backend Service is a RESTful API designed to facilitate efficient data management for the Profiler project.
This service offers a comprehensive set of HTTP methods to enable seamless interaction with data.
The primary purpose of this backend service is to handle data validation, storage, and retrieval,
ensuring the integrity and accessibility of information.

### Features

* **Data Manipulation**: The service provides endpoints for various CRUD (Create, Read, Update, Delete) operations, allowing users to perform actions on the data associated with the Profiler project.

* **Validation**: Robust data validation mechanisms are in place to ensure that incoming data meets the required criteria before being processed. This helps maintain data quality and consistency.

* **Database Integration**: The backend service seamlessly integrates with a database to persistently store data. This ensures that information is retained even after server restarts or failures.

* **RESTful Architecture**: Built on REST principles, the service offers a standardized and predictable interface for communication. Resources are logically organized, and HTTP methods correspond to specific actions.

### Technologies 

* Java 17
* Spring Boot 3
* Spring DATA JPA
* MySQL 8
* Liquibase
* Spring Doc OpenApi 3.0
* JUnit5 / Mockito / Testcontainers
* MapStruct
* Maven
* Lombok
* Docker

### Endpoints

All information related to the endpoints can be located in [Confluence](https://conf.it-academy.by/display/PROF/Prof_DEV+space)
You also can explore and test all the endpoints using Swagger. Here's how:

1. Make sure your application is up and running at `http://localhost:8080`.

2. Open your web browser and navigate to [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/).

3. You will see the Swagger UI interface, which provides information about all available endpoints and allows you to test them.

## Installation

Follow these instructions to successfully set up the project on your computer.

### Manual Setup 
1. Install and configure MySQL:

   * Download and install MySQL 8 from the [official website](https://dev.mysql.com/downloads/).
   * Start the MySQL server.
   * Create a database named profiler-db or adjust the database name in the DATABASE_URL environment variable.

2. Clone the repository to your local computer by running the following command:
    ```
    git clone https://git.it-academy.by/bootcamp/dev.git
    ```
3. Open your IDE.

4. Open the project by selecting "Open" and providing the path to the project directory.

5. Setup environment variables

    ```
    CORS_ALLOWED_METHODS - none
    CORS_ALLOWED_ORIGINS - none
    DATABASE_URL - Database URL. (Example: "jdbc:mysql://localhost:3306/profiler-db" or "jdbc:mysql://192.168.205.200:3306/profiler-db")
    DATABASE_USERNAME - Database username.
    DATABASE_PASSWORD - Database password.
    JWT_SECRET_PHRASE - JWT token secret phrase.
    IMAGES_STORAGE_FOLDER - Full path to storage images directory.
    ```
6. Run the project.

### Docker Setup
Make sure you have [Maven](https://maven.apache.org/) and [Docker Compose](https://docs.docker.com/compose/) installed.

In the terminal, navigate to the project's root directory.

Execute the following command to build the project and create the Docker image:

```
mvn clean install
docker-compose up
```

After completing these steps, your project should be successfully installed and running.
