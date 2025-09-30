package com.jobtrackr.backend.dto;

import com.jobtrackr.backend.entity.enums.StageStatus;
import com.jobtrackr.backend.entity.enums.StageType;

import java.time.LocalDateTime;

public record StageUpdateRequestDTO(
        StageType stageType,
        LocalDateTime scheduledDate,
        StageStatus status,
        String feedback
) {}

