package com.codewithzea.myprojecttracker.security.auth;

import com.codewithzea.myprojecttracker.security.JwtService;
import com.codewithzea.myprojecttracker.user.UserEntity;
import com.codewithzea.myprojecttracker.user.UserRepository;
import com.codewithzea.myprojecttracker.user.dto.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService; // Assuming you have JWT implementation

    // In your authentication service
    public AuthResponse login(UserLoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Instant accessExpiry = jwtService.getExpirationFromToken(jwtToken);
        Instant refreshExpiry = jwtService.getExpirationFromToken(refreshToken);


        return new AuthResponse(jwtToken, refreshToken, accessExpiry, refreshExpiry);
    }
}
