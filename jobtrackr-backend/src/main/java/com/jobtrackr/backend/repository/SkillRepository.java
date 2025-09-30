package com.jobtrackr.backend.repository;

import com.jobtrackr.backend.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SkillRepository extends JpaRepository<Skill, UUID> {
}
