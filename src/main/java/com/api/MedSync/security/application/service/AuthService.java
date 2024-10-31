package com.api.MedSync.security.application.service;

import com.api.MedSync.security.application.commands.LoginCommand;
import com.api.MedSync.security.application.commands.SignUpCommand;
import com.api.MedSync.security.domain.aggregate.Role;
import com.api.MedSync.security.domain.aggregate.User;
import com.api.MedSync.security.domain.model.Carer;
import com.api.MedSync.security.domain.model.Patient;
import com.api.MedSync.security.infrastructure.external.JwtProvider;
import com.api.MedSync.security.infrastructure.persistence.repository.UserRepository;
import com.api.MedSync.security.interfaces.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse signUp(SignUpCommand command) {

        if (userRepository.findByEmail(command.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        if (!command.getRole().equals(Role.PATIENT) && !command.getRole().equals(Role.CARER)) {
            throw new RuntimeException("Role is not valid");
        }

        User user;

        if (command.getRole().equals(Role.CARER)) {
            user = new Carer();
        } else if (command.getRole().equals(Role.PATIENT)) {
            user = new Patient();
        } else {
            throw new RuntimeException("Error creating user");
        }

        user.setEmail(command.getEmail());
        user.setName(command.getName());
        user.setPassword(passwordEncoder.encode(command.getPassword()));
        user.setRole(command.getRole());

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtProvider.getToken(user))
                .build();
    }

    public AuthResponse signIn(LoginCommand command) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(command.getEmail(), command.getPassword()));

        User user = userRepository.findByEmail(command.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtProvider.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}