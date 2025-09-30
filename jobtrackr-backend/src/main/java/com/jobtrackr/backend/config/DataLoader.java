package com.jobtrackr.backend.config;

import com.jobtrackr.backend.entity.Skill;
import com.jobtrackr.backend.entity.Source;
import com.jobtrackr.backend.entity.enums.SourceType;
import com.jobtrackr.backend.repository.SkillRepository;
import com.jobtrackr.backend.repository.SourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final SkillRepository skillRepository;
    private final SourceRepository sourceRepository;

    @Override
    public void run(String... args) throws Exception {
        loadSkills();
        loadSources();
    }

    private void loadSkills() {
        if (skillRepository.count() == 0) {
            List<String> skillNames = Arrays.asList(
                    "Java", "Spring Boot", "SQL", "React", "Angular", "Vue.js",
                    "Docker", "Kubernetes", "AWS", "Azure", "GCP", "Python",
                    "JavaScript", "TypeScript", "Node.js", "REST APIs", "Microservices",
                    "Git", "CI/CD", "Agile", "Scrum", "Problem Solving", "Communication"
            );

            skillNames.forEach(name -> {
                Skill skill = new Skill(null, name); // ID will be generated
                skillRepository.save(skill);
            });
            System.out.println("Skills pre-loaded successfully.");
        } else {
            System.out.println("Skills already exist, skipping pre-load.");
        }
    }

    private void loadSources() {
        if (sourceRepository.count() == 0) {
            Arrays.stream(SourceType.values()).forEach(sourceType -> {
                Source source = new Source(null, sourceType, null); // ID will be generated, linkUrl can be null
                sourceRepository.save(source);
            });
            System.out.println("Sources pre-loaded successfully.");
        } else {
            System.out.println("Sources already exist, skipping pre-load.");
        }
    }
}
