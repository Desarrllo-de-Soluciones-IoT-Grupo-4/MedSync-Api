package com.api.MedSync.security.interfaces.rest;

import com.api.MedSync.security.application.commands.LoginCommand;
import com.api.MedSync.security.application.commands.SignUpCommand;
import com.api.MedSync.security.application.service.AuthService;
import com.api.MedSync.security.interfaces.dto.AuthResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication", description = "Authentication actions")
@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody SignUpCommand signUpCommand) {
        return ResponseEntity.ok(authService.signUp(signUpCommand));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginCommand loginCommand) {
        return ResponseEntity.ok(authService.signIn(loginCommand));
    }
}
