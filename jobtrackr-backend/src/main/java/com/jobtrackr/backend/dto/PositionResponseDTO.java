package com.jobtrackr.backend.dto;

import com.jobtrackr.backend.entity.enums.SeniorityLevel;

import java.util.UUID;

public record PositionResponseDTO(
        UUID id,
        String title,
        SeniorityLevel seniority,
        String description,
        UUID companyId,
        String companyName
) {}
