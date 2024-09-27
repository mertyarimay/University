package com.education.university.business.rules;

import com.education.university.config.exception.BusinessException;
import com.education.university.entity.Section;
import com.education.university.repo.SectionRepo;
import com.education.university.repo.StudentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentRules {
    private final StudentRepo studentRepo;
    private final SectionRepo sectionRepo;
    public void existsStudentNo(String studentNo){
        if (studentRepo.existsByStudentNo(studentNo)){
            throw new BusinessException("Aynı öğrenci Numarası İle Kayıt Mevcut");
        }
    }
    public void checkSectionId(int sectionId){
        Section section=sectionRepo.findById(sectionId).orElse(null);
        if(section==null){
            throw new BusinessException("Seciton Id ye Ait Kayıt Mevcut Değildir");
        }
    }

}
