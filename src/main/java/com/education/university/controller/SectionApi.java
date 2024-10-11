package com.education.university.controller;

import com.education.university.business.model.request.CreateSectionRequestModel;
import com.education.university.business.model.request.UpdateSectionRequestModel;
import com.education.university.business.model.response.GetAllSectionResponse;
import com.education.university.business.model.response.GetByIdSectionResponse;
import com.education.university.business.service.SectionService;
import com.education.university.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/section")
public class SectionApi {
    private final SectionService sectionService;
    private final JwtUtil jwtUtil;
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
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(HttpServletRequest request) {  //parametredeki HttpServletRequest Authorization daki tokene almak için kullanılıyor
        String token = request.getHeader("Authorization");
        System.out.println("Alınan token: " + token);

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            System.out.println("Token Eksik");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token Doğrulama Başarısız");
        }

        if (token != null) {
            String userName = jwtUtil.extractUsername(token);
            boolean isValid = jwtUtil.validateToken(token, userName);
            System.out.println("Token Geçerli: " + isValid);
            System.out.println("Kullanıcı adı Çıkarıldı: " + userName);

            if (isValid) {
                List<GetAllSectionResponse> getAllSectionResponses = sectionService.getAll();
                return ResponseEntity.ok(getAllSectionResponses);
            } else {
                System.out.println("Token Doğrulama Başarısız");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token Doğrulama Başarısız");
    }





    @GetMapping("getById/{id}")
    public ResponseEntity<Object>getById(@PathVariable("id") int id){
        GetByIdSectionResponse getByIdSectionResponse=sectionService.getById(id);
        if(getByIdSectionResponse!=null){
           return ResponseEntity.ok(getByIdSectionResponse);
        }else {
         return    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu Id Ye Ait Kayıt Yoktur.");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Object>update(@RequestBody @Valid UpdateSectionRequestModel updateSectionRequestModel,@PathVariable("id") int id){
        UpdateSectionRequestModel updateSectionModel=sectionService.update(updateSectionRequestModel,id);
        if(updateSectionModel!=null){
         return    ResponseEntity.ok("Güncelleme İşlemi Başarılı Bir Şekilde Gerçekleşti");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Güncelleme İşlemi Başarısız Güncellemek istediğiniz Id ye Ait Kayıt Mevcut Değil");
        }

    }
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
