# JWT Token Validation with Swagger UI

A Spring Boot REST API demonstrating JWT generation and validation with try-catch exception handling, secured endpoints, and interactive Swagger UI.

## Requirements

- JDK 17 or newer
- Apache Maven 3.8 or newer
- Internet connection for Maven to download dependencies

Check:

```bash
java -version
mvn -version
```

## Run the application

1. Extract the ZIP.
2. Open a terminal in the `jwt-swagger-validation` folder (the folder containing `pom.xml`).
3. Run:

```bash
mvn spring-boot:run
```

4. Wait until Spring Boot reports that it has started.
5. Open this URL in your browser:

http://localhost:8080/swagger-ui/index.html

## Test using Swagger UI

### Step 1: Generate a token

Open `POST /api/auth/login`, click **Try it out**, and enter:

```json
{
  "username": "student",
  "password": "password123"
}
```

Click **Execute**. Copy the `token` value from the response.

### Step 2: Authorize Swagger

1. Click the **Authorize** button (lock icon) near the top of Swagger UI.
2. Paste the token into the value field. Because the OpenAPI scheme is HTTP Bearer, paste the token only; Swagger adds `Bearer ` automatically.
3. Click **Authorize**, then **Close**.

### Step 3: Test a valid token

Open `GET /api/user/profile`, click **Try it out**, then **Execute**.

Expected status: `200`

Example response:

```json
{
  "message": "JWT validated successfully. Access granted.",
  "username": "student"
}
```

### Step 4: Test without a token

Click **Authorize** and log out/clear the token, then execute `GET /api/user/profile`.

Expected status: `401 Unauthorized`.

### Step 5: Test an invalid token

Authorize with a modified token (for example, change one character), then execute the protected endpoint.

Expected status: `401 Unauthorized`.

## Important code

`JwtService.java` contains the required try-catch:

```java
public boolean validateToken(String token) {
    try {
        Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
        return true;
    } catch (JwtException | IllegalArgumentException e) {
        System.out.println("JWT validation failed: " + e.getMessage());
        return false;
    }
}
```

`JwtAuthenticationFilter.java` reads the `Authorization: Bearer <token>` header, validates it, and rejects invalid tokens with HTTP 401.

## Demo credentials

- Username: `student`
- Password: `password123`

These credentials and the signing key are for demonstration only. Do not use them in production. A real application should authenticate against a user store, hash passwords, and keep the JWT secret outside source code.
