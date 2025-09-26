package com.jobtrackr.backend.entity;

import com.jobtrackr.backend.entity.base.Auditable;
import com.jobtrackr.backend.entity.enums.ApplicationStatus;
import com.jobtrackr.backend.entity.enums.JobType;
import com.jobtrackr.backend.entity.enums.WorkMode;
import com.jobtrackr.backend.entity.enums.CurrencyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application extends Auditable {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private LocalDateTime applicationDate;

    private BigDecimal salaryExpected;
    private BigDecimal salaryOffered;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    private WorkMode workMode;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private Source source;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApplicationSkill> applicationSkills;

    // New compensation/benefits fields
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    @Column(nullable = false)
    private boolean hasBonusRsu = false;

    @Column(columnDefinition = "TEXT")
    private String bonusRsuDetails;

    @Column(nullable = false)
    private boolean hasRaises = false;

    @Column(columnDefinition = "TEXT")
    private String raisesDetails;

    @Column(nullable = false)
    private boolean hasThirteenthSalary = false;

    @Column(nullable = false)
    private boolean hasExtraBenefits = false;

    @Column(columnDefinition = "TEXT")
    private String extraBenefitsDetails;

    @Column(nullable = false)
    private boolean hasHealthInsurance = false;

    @Column(columnDefinition = "TEXT")
    private String healthInsuranceDetails;

    @Column(nullable = false)
    private boolean hasVacation = false;

    private Integer vacationDays;

    @Column(nullable = false)
    private boolean hasHolidays = false;

    @Column(columnDefinition = "TEXT")
    private String holidaysDetails;

    @Column(length = 255)
    private String paymentMethod;
}