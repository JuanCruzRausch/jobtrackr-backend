package com.jobtrackr.backend.controller;

import com.jobtrackr.backend.dto.SignupRequestDTO;
import com.jobtrackr.backend.entity.User;
import com.jobtrackr.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDTO request) {
        User user = authService.signup(request);
        return ResponseEntity.ok("User registered whit id: " + user.getId());
    }
}
