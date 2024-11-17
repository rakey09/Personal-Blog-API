# Personal Blog API

This is a Spring Boot-based REST API for a **Personal Blog** application. The API allows users to create, read, update, and delete blog posts and user accounts. The application also handles user authentication and authorization.

## Table of Contents
- [Technologies](#technologies)
- [Setup](#setup)
- [API Endpoints](#api-endpoints)
  - [User Endpoints](#user-endpoints)
  - [Post Endpoints](#post-endpoints)
- [Exceptions Handling](#exceptions-handling)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Technologies
This project uses the following technologies:
- **Spring Boot**: For building the RESTful web service.
- **Spring Data JPA**: For database interaction.
- **H2 Database**: An in-memory database for quick testing and development.
- **Lombok**: For reducing boilerplate code.
- **ModelMapper**: For converting entities to DTOs (Data Transfer Objects).
- **HttpServletRequest**: For handling user sessions and obtaining the current authenticated user.

## Setup

1. **Clone the repository:**
    ```bash
    git clone https://github.com/rakey09/Personal_Blog.git
    cd Personal_Blog
    ```

2. **Build and run the project:**

    If you have Maven installed:
    ```bash
    ./mvnw spring-boot:run
    ```

    Or you can run the application using an IDE like IntelliJ IDEA or Eclipse.

3. **Database Setup:**

    The application uses an **H2 database** for quick development. However, you can configure another database like MySQL or PostgreSQL by updating the `application.properties` or `application.yml` file.

    By default, the database is in-memory and will reset upon application restart.

## API Endpoints

### User Endpoints

- **Create a User:**
    - **POST /user/create**
    - Request Body: `UserRequest`
    - Response: `ApiResponse` with user data (if successful).

- **Get User by ID:**
    - **GET /user/{userId}/user**
    - Path Parameter: `userId` (the ID of the user)
    - Response: `ApiResponse` with user data (if found).

- **Get All Users:**
    - **GET /user/users**
    - Response: `ApiResponse` with a list of all users.

- **Update User:**
    - **PUT /user/update**
    - Request Body: `UpdateUserRequest`
    - Response: `ApiResponse` with updated user data (if successful).

- **Delete User:**
    - **DELETE /user/delete**
    - Response: `ApiResponse` confirming deletion (if successful).

### Post Endpoints

- **Create a Post:**
    - **POST /post/create**
    - Request Body: `PostRequest`
    - Response: `ApiResponse` with post data (if successful).

- **Get All Posts:**
    - **GET /post/Posts**
    - Response: `ApiResponse` with a list of all posts.

- **Get Post by ID:**
    - **GET /post/{postId}**
    - Path Parameter: `postId` (the ID of the post)
    - Response: `ApiResponse` with post data (if found).

- **Get Posts by Tag:**
    - **GET /post/{tag}**
    - Path Parameter: `tag` (the tag associated with the posts)
    - Response: `ApiResponse` with a list of posts by tag.

- **Update Post:**
    - **PUT /post/{postId}/update**
    - Path Parameter: `postId` (the ID of the post to update)
    - Request Body: `UpdatePostRequest`
    - Response: `ApiResponse` with updated post data (if successful).

- **Delete Post:**
    - **DELETE /post/{postId}/delete**
    - Path Parameter: `postId` (the ID of the post to delete)
    - Response: `ApiResponse` confirming deletion (if successful).

## Exceptions Handling

The application handles various exceptions to ensure smooth user experience:
- **AlreadyExistsException**: Thrown when trying to create a user or post that already exists.
- **ResourceNotFoundException**: Thrown when the requested user or post is not found.
- **UnauthorizedActionException**: Thrown when a user tries to perform an action they are not authorized to do (e.g., updating or deleting someone else's post).
- **RuntimeException**: Catches any unexpected exceptions and returns a generic error response with status code `500`.

## Testing

To test the application, you can use tools like **Postman** or **Swagger UI**.

1. **Swagger UI**: Navigate to `http://localhost:8080/swagger-ui/` to view and test the API endpoints interactively.

2. **Postman**: You can import the [Postman collection](path/to/your/postman_collection.json) to test the endpoints manually.

