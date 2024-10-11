package com.education.university.business.serviceImpl;

import com.education.university.business.model.request.UserLoginRequest;
import com.education.university.business.model.request.UserRegisterRequest;
import com.education.university.business.service.UserService;
import com.education.university.entity.User;
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
    @Override
    public UserRegisterRequest register(UserRegisterRequest userRegisterRequest) {
        User user=new User();
        user.setUserName(userRegisterRequest.getUserName());
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        userRepo.save(user);
        UserRegisterRequest userRequest=modelMapper.map(user,UserRegisterRequest.class);
        return userRequest;

    }

    @Override
    public UserLoginRequest login(UserLoginRequest userLoginRequest) {
        User user=userRepo.findByUserName(userLoginRequest.getUserName());
        if(user!=null&&passwordEncoder.matches(userLoginRequest.getPassword(),user.getPassword())){
            UserLoginRequest userRequest=modelMapper.map(user,UserLoginRequest.class);
            return userRequest;

        }
        return null;

    }
}
