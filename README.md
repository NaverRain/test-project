# Simple CRUD API with Authentication

## Overview

This is a simple CRUD API built with Spring Boot that allows users to register, log in, view a list of users, update user profiles, and delete users by their ID. The application uses JWT (JSON Web Tokens) for authentication and integrates with a PostgreSQL database running inside a Docker container.

## Features

- **User Registration** (`POST /api/register`): Allows users to create new accounts with a username and password.
- **User Login** (`POST /api/login`): Authenticates users and returns a JWT token.
- **Get Users List** (`GET /api/users`): Retrieves a list of all registered users (authenticated users only).
- **Delete User** (`DELETE /api/users/{id}`): Deletes a user by their ID (authenticated users only).
- **Update User** (`PUT /api/users/{id}`): Updates a user's details by their ID (authenticated users only).
- **JWT Authentication**: Uses JSON Web Tokens (JWT) for authentication. Users must include the JWT token in the `Authorization` header for accessing protected routes.

## Prerequisites

- Java 21 or later
- Docker
- Gradle (for building the project)

## Running the Application

### 1. Clone the Repository

Clone the repository to your local machine:

git clone https://github.com/naverrain/test-project.git

cd test-project

### 2. Set up environment variables
Create a .env file in the root directory with the following environment variables:

- SPRING_DATASOURCE_URL=`your_database_path`
- SPRING_DATASOURCE_USERNAME=`your_username`
- SPRING_DATASOURCE_PASSWORD=`your_password`
- DATABASE_NAME=`your_db_name`
- JWT_SECRET=`your_secret_key`

Replace `your_database_path` with your PostgreSQL database path.

Replace `your_db_name` with your PostgreSQL database name.

Replace `your_username` and your_password with your PostgreSQL credentials.

Replace `your_secret_key` with a secure secret key for JWT generation.

### 3. Start the [PostgreSQL](https://www.postgresql.org/) Database with Docker
To run PostgreSQL in a Docker container, execute the following command:

`docker-compose up db`

This will start a [PostgreSQL](https://www.postgresql.org/) container with the database credentials provided in the `.env` file.

### 4. Build the Application
Build the application using [Gradle](https://gradle.org/):

`./gradlew clean build`

### 5. Run the Application
You can now run the Spring Boot application:

`docker-compose up app`

This will start the Spring Boot application and connect it to the PostgreSQL database.

### 6. Access the Application
The application will be available at http://localhost:8080.

`Tip`: For a better experience, consider using [Postman](https://www.postman.com/) for testing the API.

## API Endpoints

* ### POST /api/register

**Description**: Register a new user.

**Request Body**:

`{
"username": "newuser",
"password": "password123"
}`

**Response**: Success message indicating the user was created.


* ### POST /api/login
**Description**: Authenticate a user and obtain a JWT token.

**Request Body**:

`{
  "username": "newuser",
  "password": "password123"
}
`

**Response**:

`{
"token": "your_jwt_token_here"
}`


* ### GET /api/users
**Description**: Get a list of all registered users (requires authentication).

**Headers**:
Authorization: Bearer `{your_jwt_token_here}`

**Response**:


``  {
    "id": 1,
    "username": "newuser",
    "password": "`encoded_password`"
  }``


* ### DELETE /api/users/{id}
**Description**: Delete a user by their ID (requires authentication).

**Headers**:
Authorization: Bearer `{your_jwt_token_here}`

**Response**: Success message indicating the user was deleted.

* ### PUT /api/users/{id}
**Description**: Update a user by their ID (requires authentication).

**Headers**:
Authorization: Bearer `{your_jwt_token_here}`

**Request Body**:

`{
"username": "newuser123",
"password": "password123"
}`

**Response**:

``[
{
"id": 1,
"username": "newuser123",
"password": "`encoded_password`"
}
]``

## Troubleshooting
### PostgreSQL Container Issues: 
If the PostgreSQL container is not starting, ensure the .env file is properly configured with the correct database credentials.

### JWT Authentication Errors: 
If you encounter errors related to JWT authentication, check that the Authorization header is correctly set in your API requests, with the prefix Bearer followed by the JWT token.
