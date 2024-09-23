package com.education.university.repo;

import com.education.university.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionRepo extends JpaRepository<Section,Integer> {
    boolean existsBySectionName(String sectionName);
}
