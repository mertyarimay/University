package com.education.university.business.rules;

import com.education.university.config.exception.BusinessException;
import com.education.university.entity.Role;
import com.education.university.repo.RoleRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleRules {
    private final RoleRepo roleRepo;

    public void existsByName(String name){
      boolean roleName= roleRepo.existsByName(name);
        if (roleName==true){
            throw new BusinessException("Aynı İsimde Role Kaydı Vardır");

        }

    }
    public void roleIdCheck(Integer roleId){
        Role role=roleRepo.findById(roleId).orElse(null);
        if(role==null){
            throw new BusinessException("Bu Role Ait Kayıt YOKTUR");
        }
    }
}
