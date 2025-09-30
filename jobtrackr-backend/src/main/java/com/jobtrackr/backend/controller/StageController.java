package com.jobtrackr.backend.controller;

import com.jobtrackr.backend.dto.StageCreateRequestDTO;
import com.jobtrackr.backend.dto.StageResponseDTO;
import com.jobtrackr.backend.dto.StageUpdateRequestDTO;
import com.jobtrackr.backend.service.StageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class StageController {

    private final StageService service;

    @GetMapping("/api/applications/{applicationId}/stages")
    public ResponseEntity<List<StageResponseDTO>> listByApplication(
            @RequestAttribute("userId") UUID userId,
            @PathVariable UUID applicationId
    ) {
        return ResponseEntity.ok(service.listByApplication(userId, applicationId));
    }

    @PostMapping("/api/applications/{applicationId}/stages")
    public ResponseEntity<StageResponseDTO> create(
            @RequestAttribute("userId") UUID userId,
            @PathVariable UUID applicationId,
            @Valid @RequestBody StageCreateRequestDTO request
    ) {
        StageResponseDTO dto = service.create(userId, applicationId, request);
        return ResponseEntity.created(URI.create("/api/stages/" + dto.id())).body(dto);
    }

    @PutMapping("/api/stages/{stageId}")
    public ResponseEntity<StageResponseDTO> update(
            @RequestAttribute("userId") UUID userId,
            @PathVariable UUID stageId,
            @Valid @RequestBody StageUpdateRequestDTO request
    ) {
        return ResponseEntity.ok(service.update(userId, stageId, request));
    }

    @DeleteMapping("/api/stages/{stageId}")
    public ResponseEntity<Void> delete(
            @RequestAttribute("userId") UUID userId,
            @PathVariable UUID stageId
    ) {
        service.delete(userId, stageId);
        return ResponseEntity.noContent().build();
    }
}

