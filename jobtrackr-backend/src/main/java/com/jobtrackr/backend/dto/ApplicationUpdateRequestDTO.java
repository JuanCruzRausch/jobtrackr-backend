package com.jobtrackr.backend.dto;

import com.jobtrackr.backend.entity.enums.ApplicationStatus;
import com.jobtrackr.backend.entity.enums.CurrencyType;
import com.jobtrackr.backend.entity.enums.JobType;
import com.jobtrackr.backend.entity.enums.WorkMode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ApplicationUpdateRequestDTO(
        UUID companyId,
        UUID positionId,
        UUID sourceId,
        ApplicationStatus status,
        LocalDateTime applicationDate,
        BigDecimal salaryExpected,
        BigDecimal salaryOffered,
        CurrencyType currencyType,
        JobType jobType,
        WorkMode workMode,
        @Size(max = 255) String location,
        @Size(max = 10_000) String notes,

        Boolean hasBonusRsu,
        @Size(max = 10_000) String bonusRsuDetails,

        Boolean hasRaises,
        @Size(max = 10_000) String raisesDetails,

        Boolean hasThirteenthSalary,

        Boolean hasExtraBenefits,
        @Size(max = 10_000) String extraBenefitsDetails,

        Boolean hasHealthInsurance,
        @Size(max = 10_000) String healthInsuranceDetails,

        Boolean hasVacation,
        @Min(0) @Max(365) Integer vacationDays,

        Boolean hasHolidays,
        @Size(max = 10_000) String holidaysDetails,

        @Size(max = 255) String paymentMethod
) {}