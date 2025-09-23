package com.jobtrackr.backend.service;

import com.jobtrackr.backend.dto.LoginRequestDTO;
import com.jobtrackr.backend.dto.LoginResponseDTO;
import com.jobtrackr.backend.dto.MeResponseDTO;
import com.jobtrackr.backend.dto.SignupRequestDTO;
import com.jobtrackr.backend.entity.Application;
import com.jobtrackr.backend.entity.User;
import com.jobtrackr.backend.repository.UserRepository;
import com.jobtrackr.backend.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Value("${jwt.expirationMs}")
    private long expirationMs;

    public User signup(SignupRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(UNAUTHORIZED,"The email you provided is already in use.");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email o contraseña incorrecta"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Email o contraseña incorrecta");
        }

        String token = jwtUtils.generateToken(user);
        return new LoginResponseDTO(token, "Bearer", expirationMs, user.getId(), user.getEmail());
    }

    public MeResponseDTO getMe(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(("User not found")));

        return new MeResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getApplications()
                        .stream()
                        .map(Application::getId)
                        .collect(Collectors.toList())
        );
    }
}
