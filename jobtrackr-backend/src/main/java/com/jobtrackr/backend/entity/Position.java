package com.jobtrackr.backend.entity;

import com.jobtrackr.backend.entity.base.Auditable;
import com.jobtrackr.backend.entity.enums.SeniorityLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "positions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position extends Auditable {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private SeniorityLevel seniority;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    // Relación real con Application
    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    private List<Application> applications;

    // Opcional: si querés manejar tech stack como JSON lo hablamos después
    // Por ahora podemos dejarlo en Application-Skill
}
