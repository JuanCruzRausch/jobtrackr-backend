package com.jobtrackr.backend.dto;

import com.jobtrackr.backend.entity.enums.CompanySize;

import java.util.UUID;

public record CompanyResponseDTO(
        UUID id,
        String name,
        String industry,
        CompanySize size,
        String website,
        String description
) {}
