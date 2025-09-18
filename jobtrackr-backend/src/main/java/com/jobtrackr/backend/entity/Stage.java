package com.jobtrackr.backend.entity;

import com.jobtrackr.backend.entity.base.Auditable;
import com.jobtrackr.backend.entity.enums.StageStatus;
import com.jobtrackr.backend.entity.enums.StageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stage extends Auditable {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    // Relación con Application (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Enumerated(EnumType.STRING)
    private StageType stageType;

    private LocalDateTime scheduledDate;

    @Enumerated(EnumType.STRING)
    private StageStatus status;

    @Column(columnDefinition = "TEXT")
    private String feedback;
}
