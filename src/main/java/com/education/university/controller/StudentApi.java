package com.education.university.controller;

import com.education.university.business.model.request.CreateStudentRequestModel;
import com.education.university.business.model.request.UpdateStudentRequestModel;
import com.education.university.business.model.response.GetAllSectionIdStudentResponse;
import com.education.university.business.model.response.GetAllStudentResponse;
import com.education.university.business.model.response.GetByIdStudentResponse;
import com.education.university.business.service.StudentService;
import com.education.university.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/student")
public class StudentApi {
    private final StudentService studentService;
    private final JwtUtil jwtUtil;
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody @Valid CreateStudentRequestModel createStudentRequestModel){
        CreateStudentRequestModel createStudentModel=studentService.add(createStudentRequestModel);
        if(createStudentModel!=null){
            return ResponseEntity.ok("Kayıt İşlemi Başarılı");
        }else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kayıt İşlemi Başarısız");
        }
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllStudents() {
        List<GetAllStudentResponse>getAllStudentResponses=studentService.getAll();
        return ResponseEntity.ok(getAllStudentResponses);
    }




    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/getById/{id}")
    public ResponseEntity<Object>getById(@PathVariable("id")int id){
        GetByIdStudentResponse getByIdStudentResponse=studentService.getById(id);
        if(getByIdStudentResponse!=null){
            return ResponseEntity.ok(getByIdStudentResponse);
        }else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu Idye Ait Kayıt Yoktur");
        }

    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object>update(@RequestBody @Valid UpdateStudentRequestModel updateStudentRequestModel,@PathVariable("id")int id){
        UpdateStudentRequestModel updateStudentRequest=studentService.update(updateStudentRequestModel,id);
        if(updateStudentRequest!=null){
            return ResponseEntity.ok("Güncelleme İşleminiz Başarılı Bir Şekilde Gerçekleşti");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Girdiğiğiniz ıd ye ait kayıt bulunamadı güncelleme işlemi Başarısız");
        }

    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object>delete(@PathVariable("id") int id){
        boolean delete=studentService.delete(id);
        if(delete==true){
            return ResponseEntity.ok("Silme İşleminiz Başarılı Bir Şekilde Gerçekleşti");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Girdiğiniz Id ye AİT kayıt bulunamadı SİLME İŞLEMİ BAŞARISIZ");
        }
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?>getAllSectionId(Optional<Integer>sectionId){
        List<GetAllSectionIdStudentResponse> getAllSectionIdStudentResponses=studentService.getAllSectionId(sectionId);
        if(getAllSectionIdStudentResponses!=null){
            return ResponseEntity.ok(getAllSectionIdStudentResponses);
        }else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lütfen öğrenci listesini görmek istediğiniz bölüm Id Sİ girin");
        }

    }





}
