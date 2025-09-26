package com.jobtrackr.backend.controller;

import com.jobtrackr.backend.dto.*;
import com.jobtrackr.backend.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService service;

    @GetMapping
    public ResponseEntity<List<ApplicationResponseDTO>> list(
            @RequestAttribute("userId") UUID userId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String source
    ) {
        return ResponseEntity.ok(service.list(userId, status, company, source));
    }

    @PostMapping
    public ResponseEntity<ApplicationResponseDTO> create(
            @RequestAttribute("userId") UUID userId,
            @Valid @RequestBody ApplicationCreateRequestDTO request
    ) {
        return ResponseEntity.ok(service.create(userId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> get(
            @RequestAttribute("userId") UUID userId,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(service.get(userId, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> update(
            @RequestAttribute("userId") UUID userId,
            @PathVariable UUID id,
            @Valid @RequestBody ApplicationUpdateRequestDTO request
    ) {
        return ResponseEntity.ok(service.update(userId, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestAttribute("userId") UUID userId,
            @PathVariable UUID id
    ) {
        service.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}
