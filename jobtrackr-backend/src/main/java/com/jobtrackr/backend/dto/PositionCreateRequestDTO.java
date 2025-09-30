package com.jobtrackr.backend.dto;

import com.jobtrackr.backend.entity.enums.SeniorityLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PositionCreateRequestDTO(
        @NotBlank @Size(max = 255) String title,
        @NotNull UUID companyId,
        SeniorityLevel seniority,
        @Size(max = 10000) String description
) {}
