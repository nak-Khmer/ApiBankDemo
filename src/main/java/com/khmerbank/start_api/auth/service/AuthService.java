package com.khmerbank.start_api.auth.service;

import com.khmerbank.start_api.auth.dto.AuthResponse;
import com.khmerbank.start_api.auth.dto.LoginRequest;
import com.khmerbank.start_api.auth.dto.RegisterRequest;
import com.khmerbank.start_api.security.JwtService;
import com.khmerbank.start_api.user.model.Role;
import com.khmerbank.start_api.user.model.User;
import com.khmerbank.start_api.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already exists: " + request.username());
        }

        User user = new User(
                request.username(),
                passwordEncoder.encode(request.password()), // អ៊ិនគ្រីប Password ជាមួយ BCrypt
                request.fullName(),
                Role.ROLE_USER
        );

        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        // ផ្ទៀងផ្ទាត់ Username និង Password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }
}
