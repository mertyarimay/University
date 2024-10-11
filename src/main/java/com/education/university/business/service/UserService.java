package com.education.university.business.service;

import com.education.university.business.model.request.UserLoginRequest;
import com.education.university.business.model.request.UserRegisterRequest;
import com.education.university.entity.User;

public interface UserService {
    UserRegisterRequest register(UserRegisterRequest userRegisterRequest);
    UserLoginRequest login(UserLoginRequest userLoginRequest);
}
