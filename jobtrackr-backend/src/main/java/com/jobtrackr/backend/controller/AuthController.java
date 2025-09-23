package com.jobtrackr.backend.controller;

import com.jobtrackr.backend.dto.LoginRequestDTO;
import com.jobtrackr.backend.dto.LoginResponseDTO;
import com.jobtrackr.backend.dto.MeResponseDTO;
import com.jobtrackr.backend.dto.SignupRequestDTO;
import com.jobtrackr.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MeResponseDTO> signup(@Valid @RequestBody SignupRequestDTO request) {
        MeResponseDTO created = authService.signupAndReturnProfile(request);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponseDTO> getMe(@RequestAttribute("userId") UUID userId) {
        return ResponseEntity.ok(authService.getMe(userId));
    }
}
