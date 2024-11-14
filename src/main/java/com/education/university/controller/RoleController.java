package com.education.university.controller;

import com.education.university.business.model.request.CreateRoleRequestModel;
import com.education.university.business.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;
    @PostMapping("/create")
    public ResponseEntity<Object>create(@RequestBody CreateRoleRequestModel createRoleRequestModel){
        CreateRoleRequestModel createRoleModel=roleService.create(createRoleRequestModel);
        if(createRoleModel!=null){
          return   ResponseEntity.ok("Role Kayıt İşleminiz Başarılı bir Şekilde Gerçekleşti");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role Kayıt İşlemi Başarısız");
        }
    }
}
