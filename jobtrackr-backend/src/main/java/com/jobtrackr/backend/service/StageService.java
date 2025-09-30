package com.jobtrackr.backend.service;

import com.jobtrackr.backend.dto.StageCreateRequestDTO;
import com.jobtrackr.backend.dto.StageResponseDTO;
import com.jobtrackr.backend.dto.StageUpdateRequestDTO;
import com.jobtrackr.backend.entity.Application;
import com.jobtrackr.backend.entity.Stage;
import com.jobtrackr.backend.repository.ApplicationRepository;
import com.jobtrackr.backend.repository.StageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StageService {

    private final StageRepository stageRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional(readOnly = true)
    public List<StageResponseDTO> listByApplication(UUID userId, UUID applicationId) {
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Application not found"));
        ensureOwnership(app, userId);
        return stageRepository.findByApplicationIdOrderByScheduledDateAsc(applicationId)
                .stream().map(this::toDto).toList();
    }

    @Transactional
    public StageResponseDTO create(UUID userId, UUID applicationId, StageCreateRequestDTO req) {
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Application not found"));
        ensureOwnership(app, userId);

        Stage s = new Stage();
        s.setApplication(app);
        s.setStageType(req.stageType());
        s.setScheduledDate(req.scheduledDate());
        s.setStatus(req.status());
        s.setFeedback(req.feedback());

        return toDto(stageRepository.save(s));
    }

    @Transactional
    public StageResponseDTO update(UUID userId, UUID stageId, StageUpdateRequestDTO req) {
        Stage s = stageRepository.findById(stageId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Stage not found"));
        ensureOwnership(s.getApplication(), userId);

        if (req.stageType() != null) s.setStageType(req.stageType());
        if (req.scheduledDate() != null) s.setScheduledDate(req.scheduledDate());
        if (req.status() != null) s.setStatus(req.status());
        if (req.feedback() != null) s.setFeedback(req.feedback());

        return toDto(s);
    }

    @Transactional
    public void delete(UUID userId, UUID stageId) {
        Stage s = stageRepository.findById(stageId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Stage not found"));
        ensureOwnership(s.getApplication(), userId);
        stageRepository.delete(s);
    }

    private void ensureOwnership(Application app, UUID userId) {
        if (app.getUser() == null || app.getUser().getId() == null || !app.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(FORBIDDEN, "Forbidden");
        }
    }

    private StageResponseDTO toDto(Stage s) {
        return new StageResponseDTO(
                s.getId(),
                s.getStageType(),
                s.getScheduledDate(),
                s.getStatus(),
                s.getFeedback()
        );
    }
}
