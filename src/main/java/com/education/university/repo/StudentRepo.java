package com.education.university.repo;

import com.education.university.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {
    boolean existsByStudentNo(String studentNo);


    List<Student>findBySectionId(Integer sectionId);
}
