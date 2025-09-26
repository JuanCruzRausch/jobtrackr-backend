package com.jobtrackr.backend.repository;

import com.jobtrackr.backend.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SourceRepository extends JpaRepository<Source, UUID> {
}
