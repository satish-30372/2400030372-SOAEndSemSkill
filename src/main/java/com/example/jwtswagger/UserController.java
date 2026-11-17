package com.example.jwtswagger;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    public Map<String, String> profile(Authentication authentication) {
        return Map.of(
                "message", "JWT validated successfully. Access granted.",
                "username", authentication.getName()
        );
    }
}
