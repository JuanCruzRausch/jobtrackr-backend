package com.jobtrackr.backend.entity;

import com.jobtrackr.backend.entity.base.Auditable;
import com.jobtrackr.backend.entity.enums.CompanySize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company extends Auditable {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String industry;

    @Enumerated(EnumType.STRING)
    private CompanySize size;

    private String website;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<Position> positions;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<Application> applications;

}
