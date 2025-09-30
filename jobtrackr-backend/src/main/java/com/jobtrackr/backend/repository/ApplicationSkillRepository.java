package com.jobtrackr.backend.repository;

import com.jobtrackr.backend.entity.ApplicationSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplicationSkillRepository extends JpaRepository<ApplicationSkill, UUID> {
}
