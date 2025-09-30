package com.jobtrackr.backend.service;

import com.jobtrackr.backend.dto.CompanyCreateRequestDTO;
import com.jobtrackr.backend.dto.CompanyResponseDTO;
import com.jobtrackr.backend.dto.CompanyUpdateRequestDTO;
import com.jobtrackr.backend.entity.Company;
import com.jobtrackr.backend.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public List<CompanyResponseDTO> list() {
        return companyRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public CompanyResponseDTO get(UUID id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Company not found"));
        return toDto(company);
    }

    @Transactional
    public CompanyResponseDTO create(CompanyCreateRequestDTO req) {
        Company company = new Company();
        company.setName(req.name());
        company.setIndustry(req.industry());
        company.setSize(req.size());
        company.setWebsite(req.website());
        company.setDescription(req.description());
        return toDto(companyRepository.save(company));
    }

    @Transactional
    public CompanyResponseDTO update(UUID id, CompanyUpdateRequestDTO req) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Company not found"));
        if (req.name() != null) company.setName(req.name());
        if (req.industry() != null) company.setIndustry(req.industry());
        if (req.size() != null) company.setSize(req.size());
        if (req.website() != null) company.setWebsite(req.website());
        if (req.description() != null) company.setDescription(req.description());
        return toDto(company);
    }

    @Transactional
    public void delete(UUID id) {
        Company c = companyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Company not found"));
        companyRepository.delete(c);
    }

    private CompanyResponseDTO toDto(Company c) {
        return new CompanyResponseDTO(
                c.getId(),
                c.getName(),
                c.getIndustry(),
                c.getSize(),
                c.getWebsite(),
                c.getDescription()
        );
    }
}
