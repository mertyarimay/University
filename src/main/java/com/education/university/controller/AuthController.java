package com.education.university.controller;

import com.education.university.business.model.request.UserLoginRequest;
import com.education.university.business.model.request.UserRegisterRequest;
import com.education.university.business.service.UserService;
import com.education.university.config.exception.BusinessException;
import com.education.university.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;


    @PostMapping("register")
    public ResponseEntity<Object> register (@RequestBody UserRegisterRequest userRegisterRequest){
        UserRegisterRequest userRequest=userService.register(userRegisterRequest);
        if(userRequest!=null){
            return ResponseEntity.ok("Kayıt işleminiz başarılı ");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kayıt işlemi Başarısız");
        }

    }


    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest userLoginRequest){
        UserLoginRequest loginRequest=userService.login(userLoginRequest);
        if(loginRequest!=null){
           return jwtUtil.generateToken(userLoginRequest.getUserName());


        }
        else {
            throw new BusinessException("GEÇERSİZ KULLANICI ADI VEYA ŞİFRE");

        }
    }

}
