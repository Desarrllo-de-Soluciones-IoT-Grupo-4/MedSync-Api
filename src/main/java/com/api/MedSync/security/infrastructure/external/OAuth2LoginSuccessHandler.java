package com.api.MedSync.security.infrastructure.external;

import com.api.MedSync.security.domain.aggregate.User;
import com.api.MedSync.security.domain.model.Patient;
import com.api.MedSync.security.infrastructure.persistence.repository.UserRepository;
import com.api.MedSync.security.interfaces.mapper.OAuth2ToUserMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        User mappedUser = OAuth2ToUserMapper.map(oAuth2User.getAttributes());

        User newUser = new Patient();
        newUser.setEmail(mappedUser.getEmail());
        newUser.setName(mappedUser.getName());
        newUser.setPassword(mappedUser.getPassword());
        newUser.setRole(mappedUser.getRole());

        Optional<User> existingUser = userRepository.findByEmail(mappedUser.getEmail());

        User finalUser = existingUser.orElseGet(() -> userRepository.save(newUser));

        String jwtToken = jwtProvider.getToken(finalUser);

        response.addHeader("Authorization", "Bearer " + jwtToken);

        response.sendRedirect("/");
    }
}