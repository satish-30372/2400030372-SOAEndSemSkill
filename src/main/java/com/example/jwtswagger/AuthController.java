package com.example.jwtswagger;

import io.swagger.v3.oas.annotations.Operation;\nimport org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        // Demo-only credentials. Replace with a real user store and password hashing.
        if (!"student".equals(request.username())
                || !"password123".equals(request.password())) {
            throw new InvalidCredentialsException();
        }

        return new LoginResponse(jwtService.generateToken(request.username()));
    }

    public record LoginRequest(String username, String password) {}
    public record LoginResponse(String token) {}

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException.class)
    public String invalidCredentials(InvalidCredentialsException ex) {
        return "Invalid username or password";
    }

    static class InvalidCredentialsException extends RuntimeException {}
}
