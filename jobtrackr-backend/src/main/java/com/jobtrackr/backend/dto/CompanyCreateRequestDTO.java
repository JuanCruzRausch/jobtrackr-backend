package com.jobtrackr.backend.dto;

import com.jobtrackr.backend.entity.enums.CompanySize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CompanyCreateRequestDTO(
        @NotBlank @Size(max = 255) String name,
        @Size(max = 255) String industry,
        CompanySize size,
        @Size(max = 255) String website,
        @Size(max = 10000) String description
) {}
