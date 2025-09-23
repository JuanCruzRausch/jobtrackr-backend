package com.jobtrackr.backend.controller;

import com.jobtrackr.backend.dto.LoginRequestDTO;
import com.jobtrackr.backend.dto.LoginResponseDTO;
import com.jobtrackr.backend.dto.MeResponseDTO;
import com.jobtrackr.backend.dto.SignupRequestDTO;
import com.jobtrackr.backend.entity.User;
import com.jobtrackr.backend.security.JwtUtils;
import com.jobtrackr.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDTO request) {
        User user = authService.signup(request);
        return ResponseEntity.ok("User registered whit id: " + user.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponseDTO> getMe(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        UUID userId = jwtUtils.getUserIdFromJwt(jwt);

        MeResponseDTO response = authService.getMe(userId);

        return ResponseEntity.ok(response);
    }
}
