package com.jobtrackr.backend.controller;

import com.jobtrackr.backend.dto.CompanyCreateRequestDTO;
import com.jobtrackr.backend.dto.CompanyResponseDTO;
import com.jobtrackr.backend.dto.CompanyUpdateRequestDTO;
import com.jobtrackr.backend.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService service;

    @GetMapping
    public ResponseEntity<List<CompanyResponseDTO>> list() {
        return ResponseEntity.ok(service.list());
    }

    @PostMapping
    public ResponseEntity<CompanyResponseDTO> create(@Valid @RequestBody CompanyCreateRequestDTO request) {
        CompanyResponseDTO company = service.create(request);
        return ResponseEntity.created(URI.create("/api/companies" + company.id())).body(company);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody CompanyUpdateRequestDTO request) {
        return ResponseEntity.ok(service.update(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
