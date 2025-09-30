package com.jobtrackr.backend.repository;

import com.jobtrackr.backend.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StageRepository extends JpaRepository<Stage, UUID> {
    List<Stage> findByApplicationIdOrderByScheduledDateAsc(UUID applicationId);
}

