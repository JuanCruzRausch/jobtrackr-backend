package com.jobtrackr.backend.dto;

import com.jobtrackr.backend.entity.enums.ApplicationStatus;
import com.jobtrackr.backend.entity.enums.CurrencyType;
import com.jobtrackr.backend.entity.enums.JobType;
import com.jobtrackr.backend.entity.enums.WorkMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ApplicationResponseDTO(
        UUID id,
        UUID userId,
        UUID companyId,
        String companyName,
        UUID positionId,
        String positionName,
        ApplicationStatus status,
        LocalDateTime applicationDate,
        BigDecimal salaryExpected,
        BigDecimal salaryOffered,
        CurrencyType currencyType,
        JobType jobType,
        WorkMode workMode,
        String location,
        String sourceName,
        String notes,
        List<String> skills,
        List<StageResponseDTO> stages,

        boolean hasBonusRsu,
        String bonusRsuDetails,
        boolean hasRaises,
        String raisesDetails,
        boolean hasThirteenthSalary,
        boolean hasExtraBenefits,
        String extraBenefitsDetails,
        boolean hasHealthInsurance,
        String healthInsuranceDetails,
        boolean hasVacation,
        Integer vacationDays,
        boolean hasHolidays,
        String holidaysDetails,
        String paymentMethod
) {}
