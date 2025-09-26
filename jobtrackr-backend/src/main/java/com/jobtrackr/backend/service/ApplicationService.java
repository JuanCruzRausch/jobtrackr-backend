package com.jobtrackr.backend.service;

import com.jobtrackr.backend.dto.ApplicationCreateRequestDTO;
import com.jobtrackr.backend.dto.ApplicationResponseDTO;
import com.jobtrackr.backend.dto.ApplicationUpdateRequestDTO;
import com.jobtrackr.backend.entity.Application;
import com.jobtrackr.backend.entity.Company;
import com.jobtrackr.backend.entity.Position;
import com.jobtrackr.backend.entity.Source;
import com.jobtrackr.backend.entity.enums.ApplicationStatus;
import com.jobtrackr.backend.repository.ApplicationRepository;
import com.jobtrackr.backend.repository.CompanyRepository;
import com.jobtrackr.backend.repository.PositionRepository;
import com.jobtrackr.backend.repository.SourceRepository;
import com.jobtrackr.backend.repository.UserRepository;
import com.jobtrackr.backend.repository.spec.ApplicationSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CompanyRepository companyRepository;
    private final PositionRepository positionRepository;
    private final SourceRepository sourceRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ApplicationResponseDTO> list(UUID userId, String status, String company, String source) {
        ApplicationStatus st = null;
        if (status != null && !status.isBlank()) {
            try {
                st = ApplicationStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new ResponseStatusException(BAD_REQUEST, "Invalid status");
            }
        }

        Specification<Application> spec = Specification
                .where(ApplicationSpecifications.belongsToUser(userId))
                .and(ApplicationSpecifications.hasStatus(st))
                .and(ApplicationSpecifications.hasCompanyName(company))
                .and(ApplicationSpecifications.hasSourceName(source));

        return applicationRepository.findAll(spec).stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ApplicationResponseDTO get(UUID userId, UUID id) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Application not found"));
        ensureOwnership(app, userId);
        return toDto(app);
    }

    @Transactional
    public ApplicationResponseDTO create(UUID userId, ApplicationCreateRequestDTO req) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
        Company company = companyRepository.findById(req.companyId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Company not found"));

        Position position = null;
        if (req.positionId() != null) {
            position = positionRepository.findById(req.positionId())
                    .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Position not found"));
        }

        Source source = null;
        if (req.sourceId() != null) {
            source = sourceRepository.findById(req.sourceId())
                    .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Source not found"));
        }

        Application app = new Application();
        app.setUser(user);
        app.setCompany(company);
        app.setPosition(position);
        app.setSource(source);
        app.setStatus(req.status());
        app.setApplicationDate(req.applicationDate());
        app.setSalaryExpected(req.salaryExpected());
        app.setSalaryOffered(req.salaryOffered());
        app.setCurrencyType(req.currencyType());
        app.setJobType(req.jobType());
        app.setWorkMode(req.workMode());
        app.setLocation(req.location());
        app.setNotes(req.notes());

        app.setHasBonusRsu(Boolean.TRUE.equals(req.hasBonusRsu()));
        app.setBonusRsuDetails(req.bonusRsuDetails());
        app.setHasRaises(Boolean.TRUE.equals(req.hasRaises()));
        app.setRaisesDetails(req.raisesDetails());
        app.setHasThirteenthSalary(Boolean.TRUE.equals(req.hasThirteenthSalary()));
        app.setHasExtraBenefits(Boolean.TRUE.equals(req.hasExtraBenefits()));
        app.setExtraBenefitsDetails(req.extraBenefitsDetails());
        app.setHasHealthInsurance(Boolean.TRUE.equals(req.hasHealthInsurance()));
        app.setHealthInsuranceDetails(req.healthInsuranceDetails());
        app.setHasVacation(Boolean.TRUE.equals(req.hasVacation()));
        app.setVacationDays(req.vacationDays());
        app.setHasHolidays(Boolean.TRUE.equals(req.hasHolidays()));
        app.setHolidaysDetails(req.holidaysDetails());
        app.setPaymentMethod(req.paymentMethod());

        return toDto(applicationRepository.save(app));
    }

    @Transactional
    public ApplicationResponseDTO update(UUID userId, UUID id, ApplicationUpdateRequestDTO req) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Application not found"));
        ensureOwnership(app, userId);

        if (req.companyId() != null) {
            var company = companyRepository.findById(req.companyId())
                    .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Company not found"));
            app.setCompany(company);
        }
        if (req.positionId() != null) {
            var position = positionRepository.findById(req.positionId())
                    .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Position not found"));
            app.setPosition(position);
        }
        if (req.sourceId() != null) {
            var source = sourceRepository.findById(req.sourceId())
                    .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Source not found"));
            app.setSource(source);
        }

        if (req.status() != null) app.setStatus(req.status());
        if (req.applicationDate() != null) app.setApplicationDate(req.applicationDate());
        if (req.salaryExpected() != null) app.setSalaryExpected(req.salaryExpected());
        if (req.salaryOffered() != null) app.setSalaryOffered(req.salaryOffered());
        if (req.currencyType() != null) app.setCurrencyType(req.currencyType());
        if (req.jobType() != null) app.setJobType(req.jobType());
        if (req.workMode() != null) app.setWorkMode(req.workMode());
        if (req.location() != null) app.setLocation(req.location());
        if (req.notes() != null) app.setNotes(req.notes());

        if (req.hasBonusRsu() != null) app.setHasBonusRsu(req.hasBonusRsu());
        if (req.bonusRsuDetails() != null) app.setBonusRsuDetails(req.bonusRsuDetails());
        if (req.hasRaises() != null) app.setHasRaises(req.hasRaises());
        if (req.raisesDetails() != null) app.setRaisesDetails(req.raisesDetails());
        if (req.hasThirteenthSalary() != null) app.setHasThirteenthSalary(req.hasThirteenthSalary());
        if (req.hasExtraBenefits() != null) app.setHasExtraBenefits(req.hasExtraBenefits());
        if (req.extraBenefitsDetails() != null) app.setExtraBenefitsDetails(req.extraBenefitsDetails());
        if (req.hasHealthInsurance() != null) app.setHasHealthInsurance(req.hasHealthInsurance());
        if (req.healthInsuranceDetails() != null) app.setHealthInsuranceDetails(req.healthInsuranceDetails());
        if (req.hasVacation() != null) app.setHasVacation(req.hasVacation());
        if (req.vacationDays() != null) app.setVacationDays(req.vacationDays());
        if (req.hasHolidays() != null) app.setHasHolidays(req.hasHolidays());
        if (req.holidaysDetails() != null) app.setHolidaysDetails(req.holidaysDetails());
        if (req.paymentMethod() != null) app.setPaymentMethod(req.paymentMethod());

        return toDto(app);
    }

    @Transactional
    public void delete(UUID userId, UUID id) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Application not found"));
        ensureOwnership(app, userId);
        applicationRepository.delete(app);
    }

    private void ensureOwnership(Application app, UUID userId) {
        if (app.getUser() == null || app.getUser().getId() == null || !app.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(FORBIDDEN, "Forbidden");
        }
    }

    private ApplicationResponseDTO toDto(Application a) {
        return new ApplicationResponseDTO(
                a.getId(),
                a.getUser() != null ? a.getUser().getId() : null,
                a.getCompany() != null ? a.getCompany().getId() : null,
                a.getCompany() != null ? a.getCompany().getName() : null,
                a.getPosition() != null ? a.getPosition().getId() : null,
                a.getPosition() != null ? a.getPosition().getTitle() : null, // adjust to your Position field
                a.getStatus(),
                a.getApplicationDate(),
                a.getSalaryExpected(),
                a.getSalaryOffered(),
                a.getCurrencyType(),
                a.getJobType(),
                a.getWorkMode(),
                a.getLocation(),
                a.getSource() != null ? a.getSource().getName() : null,
                a.getNotes(),
                a.isHasBonusRsu(),
                a.getBonusRsuDetails(),
                a.isHasRaises(),
                a.getRaisesDetails(),
                a.isHasThirteenthSalary(),
                a.isHasExtraBenefits(),
                a.getExtraBenefitsDetails(),
                a.isHasHealthInsurance(),
                a.getHealthInsuranceDetails(),
                a.isHasVacation(),
                a.getVacationDays(),
                a.isHasHolidays(),
                a.getHolidaysDetails(),
                a.getPaymentMethod()
        );
    }
}
