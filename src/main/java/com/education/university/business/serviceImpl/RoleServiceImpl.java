package com.education.university.business.serviceImpl;

import com.education.university.business.model.request.CreateRoleRequestModel;
import com.education.university.business.rules.RoleRules;
import com.education.university.business.service.RoleService;
import com.education.university.entity.Role;
import com.education.university.repo.RoleRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;
    private final ModelMapper modelMapper;
    private final RoleRules roleRules;
    @Override
    public CreateRoleRequestModel create(CreateRoleRequestModel createRoleRequestModel) {
        roleRules.existsByName(createRoleRequestModel.getName());
        Role role=modelMapper.map(createRoleRequestModel,Role.class);
        roleRepo.save(role);
        CreateRoleRequestModel createRole=modelMapper.map(role,CreateRoleRequestModel.class);
        return createRole;


    }
}
