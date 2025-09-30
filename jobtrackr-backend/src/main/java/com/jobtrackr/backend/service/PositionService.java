package com.jobtrackr.backend.service;

import com.jobtrackr.backend.dto.PositionCreateRequestDTO;
import com.jobtrackr.backend.dto.PositionResponseDTO;
import com.jobtrackr.backend.dto.PositionUpdateRequestDTO;
import com.jobtrackr.backend.entity.Company;
import com.jobtrackr.backend.entity.Position;
import com.jobtrackr.backend.repository.CompanyRepository;
import com.jobtrackr.backend.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;
    private final CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public List<PositionResponseDTO> list() {
        return positionRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public PositionResponseDTO get(UUID id) {
        Position position = positionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Position not found"));
        return toDto(position);
    }

    @Transactional
    public PositionResponseDTO create(PositionCreateRequestDTO req) {
        Company company = companyRepository.findById(req.companyId()).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "Company not found"));
        Position position = new Position();
        position.setTitle(req.title());
        position.setCompany(company);
        position.setSeniority(req.seniority());
        position.setDescription(req.description());
        return toDto(positionRepository.save(position));
    }

    @Transactional
    public PositionResponseDTO update(UUID id, PositionUpdateRequestDTO req) {
        Position position = positionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Position not found"));
        if (req.title() != null) position.setTitle(req.title());
        if (req.seniority() != null) position.setSeniority(req.seniority());
        if (req.description() != null) position.setDescription(req.description());
        return toDto(position);
    }

    @Transactional
    public void delete(UUID id) {
        Position p = positionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Position not found"));
        positionRepository.delete(p);
    }

    private PositionResponseDTO toDto(Position p) {
        return new PositionResponseDTO(
                p.getId(),
                p.getTitle(),
                p.getSeniority(),
                p.getDescription(),
                p.getCompany() != null ? p.getCompany().getId() : null,
                p.getCompany() != null ? p.getCompany().getName() : null
        );
    }
}
