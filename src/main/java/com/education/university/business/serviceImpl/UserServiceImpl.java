package com.education.university.business.serviceImpl;

import com.education.university.business.model.request.UserLoginRequest;
import com.education.university.business.model.request.UserRegisterRequest;
import com.education.university.business.model.response.UserLoginResponse;
import com.education.university.business.rules.RoleRules;
import com.education.university.business.service.UserService;
import com.education.university.entity.Role;
import com.education.university.entity.User;
import com.education.university.repo.RoleRepo;
import com.education.university.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleRules roleRules;
    private final RoleRepo roleRepo;
    @Override
    public UserRegisterRequest register(UserRegisterRequest userRegisterRequest) {
        roleRules.roleIdCheck(userRegisterRequest.getRoleId());
        User user=new User();
        user.setUserName(userRegisterRequest.getUserName());
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        Role role=roleRepo.findById(userRegisterRequest.getRoleId()).orElse(null);
        if(role!=null){
            user.setRole(role);
        }
        userRepo.save(user);
        UserRegisterRequest userRequest=modelMapper.map(user,UserRegisterRequest.class);
        return userRequest;

    }

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        User user=userRepo.findByUserName(userLoginRequest.getUserName());

        if(user!=null&&passwordEncoder.matches(userLoginRequest.getPassword(),user.getPassword())){
            UserLoginResponse userLoginResponse=modelMapper.map(user,UserLoginResponse.class);
            userLoginResponse.setRoleName(user.getRole().getName());
            return userLoginResponse;

        }
        return null;

    }
}
