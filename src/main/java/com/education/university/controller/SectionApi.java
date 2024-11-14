package com.education.university.controller;

import com.education.university.business.model.request.CreateSectionRequestModel;
import com.education.university.business.model.request.UpdateSectionRequestModel;
import com.education.university.business.model.response.GetAllSectionResponse;
import com.education.university.business.model.response.GetByIdSectionResponse;
import com.education.university.business.service.SectionService;
import com.education.university.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/section")
public class SectionApi {
    private final SectionService sectionService;
    private final JwtUtil jwtUtil;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Object>add(@RequestBody @Valid CreateSectionRequestModel createSectionRequestModel){
        CreateSectionRequestModel createSectionModel=sectionService.add(createSectionRequestModel);
        if(createSectionModel!=null){
            return ResponseEntity.ok("Kayıt İşleminiz Başarılı bir şekilde oluşturulmuştur");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kayıt işlemi Başarısız");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")  //buraya ulaşılması için yetkiyi kontrol eder sadece bu yetkiye sahip kullanıcıların çağırmasına izin verir
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllSections() {
        List<GetAllSectionResponse> getAllSectionResponses = sectionService.getAll();
        return ResponseEntity.ok(getAllSectionResponses);
    }





    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("getById/{id}")
    public ResponseEntity<Object>getById(@PathVariable("id") int id){
        GetByIdSectionResponse getByIdSectionResponse=sectionService.getById(id);
        if(getByIdSectionResponse!=null){
           return ResponseEntity.ok(getByIdSectionResponse);
        }else {
         return    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu Id Ye Ait Kayıt Yoktur.");
        }
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object>update(@RequestBody @Valid UpdateSectionRequestModel updateSectionRequestModel,@PathVariable("id") int id){
        UpdateSectionRequestModel updateSectionModel=sectionService.update(updateSectionRequestModel,id);
        if(updateSectionModel!=null){
         return    ResponseEntity.ok("Güncelleme İşlemi Başarılı Bir Şekilde Gerçekleşti");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Güncelleme İşlemi Başarısız Güncellemek istediğiniz Id ye Ait Kayıt Mevcut Değil");
        }

    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id")int id){
        boolean delete=sectionService.delete(id);
        if(delete==true){
            return ResponseEntity.ok("Silme işleminiz Başarılı bir şekilde gerçekleşti ");
        }else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Silme İşlemi başarısız");
        }
    }

}
