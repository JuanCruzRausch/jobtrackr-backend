package com.jobtrackr.backend.dto;

import com.jobtrackr.backend.entity.enums.SeniorityLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PositionUpdateRequestDTO(
        @NotBlank @Size(max = 255) String title,
        SeniorityLevel seniority,
        @Size(max = 10000) String description
) {}
