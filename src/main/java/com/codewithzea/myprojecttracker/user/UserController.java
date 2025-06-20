package com.codewithzea.myprojecttracker.user;



import com.codewithzea.myprojecttracker.security.auth.AuthResponse;
import com.codewithzea.myprojecttracker.security.auth.AuthService;
import com.codewithzea.myprojecttracker.user.dto.UserLoginRequest;
import com.codewithzea.myprojecttracker.user.dto.UserRegistrationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class UserController {

    private final UserRegistrationService registrationService;
    private final AuthService authService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(
            @Valid @RequestBody UserRegistrationRequest request
    ) {
        log.info("Registration attempt for email: {}", request.email());
        UserEntity newUser = registrationService.registerUser(request);
        log.info("User registered successfully with ID: {}", newUser.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newUser);
    }

    @Operation(summary = "Authenticate user and get JWT token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody UserLoginRequest request
    ) {
        log.info("Login attempt for email: {}", request.email());
        AuthResponse response = authService.login(request);
        log.debug("Login successful for email: {}", request.email());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "OAuth2 success callback")
    @GetMapping("/oauth-success")
    public ResponseEntity<Map<String, String>> oauthSuccess(
            @RequestParam String token,
            @AuthenticationPrincipal UserEntity user
    ) {
        log.info("OAuth2 authentication successful for user ID: {}", user.getId());
        return ResponseEntity.ok(Map.of(
                "token", token,
                "userId", user.getId().toString(),
                "role", user.getRole().name()
        ));
    }

    @Operation(summary = "Get current user info",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/me")
    public ResponseEntity<UserEntity> getCurrentUser(
            @AuthenticationPrincipal UserEntity user
    ) {
        log.debug("Fetching current user info for ID: {}", user.getId());
        return ResponseEntity.ok(user);
    }
}