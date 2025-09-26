package com.jobtrackr.backend.controller;

import com.jobtrackr.backend.dto.PositionCreateRequestDTO;
import com.jobtrackr.backend.dto.PositionResponseDTO;
import com.jobtrackr.backend.dto.PositionUpdateRequestDTO;
import com.jobtrackr.backend.service.PositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService service;

    @GetMapping
    public ResponseEntity<List<PositionResponseDTO>> list() {
        return ResponseEntity.ok(service.list());
    }

    @PostMapping
    public ResponseEntity<PositionResponseDTO> create(
            @Valid @RequestBody PositionCreateRequestDTO request) {
        PositionResponseDTO dto = service.create(request);
        return ResponseEntity.created(URI.create("/api/positions/" + dto.id())).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionResponseDTO> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PositionResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody PositionUpdateRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }
}
