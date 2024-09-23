package com.education.university.business.rules;

import com.education.university.config.exception.BusinessException;
import com.education.university.repo.SectionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SectionRules {
    private final SectionRepo sectionRepo;

    public void existsName(String sectionName){
        if (sectionRepo.existsBySectionName(sectionName)){
            throw new BusinessException("Aynı isimde kayıt mevcuttur ");

        }
    }

}
