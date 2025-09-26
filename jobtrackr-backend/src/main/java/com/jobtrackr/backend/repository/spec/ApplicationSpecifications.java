package com.jobtrackr.backend.repository.spec;

import com.jobtrackr.backend.entity.Application;
import com.jobtrackr.backend.entity.enums.ApplicationStatus;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class ApplicationSpecifications {

    public static Specification<Application> belongsToUser(UUID userId) {
        return (root, q, cb) -> cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Application> hasStatus(ApplicationStatus status) {
        return (root, q, cb) -> status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    public static Specification<Application> hasCompanyName(String company) {
        return (root, q, cb) -> {
            if (company == null || company.isBlank()) return cb.conjunction();
            var join = root.join("company", JoinType.LEFT);
            return cb.like(cb.lower(join.get("name")), "%" + company.toLowerCase() + "%");
        };
    }

    public static Specification<Application> hasSourceName(String source) {
        return (root, q, cb) -> {
            if (source == null || source.isBlank()) return cb.conjunction();
            var join = root.join("source", JoinType.LEFT);
            return cb.like(cb.lower(join.get("name")), "%" + source.toLowerCase() + "%");
        };
    }
}
