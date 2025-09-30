package com.jobtrackr.backend.dto;

import com.jobtrackr.backend.entity.enums.StageStatus;
import com.jobtrackr.backend.entity.enums.StageType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record StageCreateRequestDTO(
        @NotNull StageType stageType,
        LocalDateTime scheduledDate,
        @NotNull StageStatus status,
        String feedback
) {}

