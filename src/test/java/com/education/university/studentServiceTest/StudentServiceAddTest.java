package com.education.university.studentServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.education.university.business.model.request.CreateStudentRequestModel;
import com.education.university.business.rules.StudentRules;
import com.education.university.business.serviceImpl.StudentServiceImpl;
import com.education.university.config.exception.BusinessException;
import com.education.university.entity.Section;
import com.education.university.entity.Student;
import com.education.university.repo.StudentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class StudentServiceAddTest {
    @Mock
    private StudentRepo studentRepo;  //mock nesnesi oluşturuluyor gerçek nesne değil test sırasında bu nesneymiş gibi kullanılıyor

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private StudentRules studentRules;

    @InjectMocks
    private StudentServiceImpl studentService;  //bu student service nesnesi oluşturur Injeck olması test edilen sınıf olmasıdır
    //yukarıdaki mock nesneleri bu sınıfa enjekte edilir.

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    } //mock nesnelerini başlatmak için kullanılır her testten önce  yazılır.

    @Test
    public void testCreateStudent() {

        CreateStudentRequestModel createStudentRequestModel = new CreateStudentRequestModel(1, "Test", "TestLastName", "12345678946", 1);
        Section section = new Section(); // Section nesnesi oluştur studentta section alanı dolddurmak için
        section.setId(1); // ID'yi ayarla
        Student student = new Student(1, "Test", "TestLastName", "12345678946", section);  //burda model  entity nesneleri oluşturulur testte kullanılıcak ve her iki nesne aynı bilgileri içermesi gerekir

        // Mock davranışları örneğin model mapperın map çağrıldığında çevirme işlemi olucak gibi
        when(modelMapper.map(createStudentRequestModel, Student.class)).thenReturn(student);
        when(studentRepo.save(student)).thenReturn(student);
        when(modelMapper.map(student, CreateStudentRequestModel.class)).thenReturn(createStudentRequestModel);

        // burasıda aslında eylem aşaması işin api kısmı denilebilir
        CreateStudentRequestModel createStudentRequest = studentService.add(createStudentRequestModel);

        // Assert bu kısımda dönen nesne null mu ve dönen nesne beklenen değermi o kontrol ediliyor
        assertNotNull(createStudentRequest);
        assertEquals(1, createStudentRequest.getId());
        assertEquals("Test", createStudentRequest.getName());
        assertEquals("TestLastName", createStudentRequest.getLastName());
        assertEquals("12345678946", createStudentRequest.getStudentNo());
        assertEquals(1, createStudentRequest.getSectionId());

        // Verify  burasıda mock metotların beklenildiği gibi çağrılıp çağrılmadığını konrol eder.
        verify(modelMapper, times(1)).map(createStudentRequestModel, Student.class);
        verify(studentRepo, times(1)).save(student);
        verify(modelMapper, times(1)).map(student, CreateStudentRequestModel.class);
    }


//aynı student no ile kayıt yapılamama testi
    @Test
    public void testCreateStudentWithStudentNo() {
        //  Test için gerekli verileri oluştur
        CreateStudentRequestModel createStudentRequestModel = new CreateStudentRequestModel();
        createStudentRequestModel.setName("Test");
        createStudentRequestModel.setLastName("TestLastName");
        createStudentRequestModel.setStudentNo("12345678946"); // Bu öğrenci numarasını kullanacağız
        createStudentRequestModel.setSectionId(1);

        // Mock davranışları: Repo'dan mevcut öğrenci numarasının olduğunu belirt
        when(studentRepo.existsByStudentNo("12345678946")).thenReturn(true); // Öğrenci numarası zaten var

        // studentRules içindeki exitsStudentNo metodunun fırlattığı istisnayı ayarlama
        doThrow(new BusinessException("Aynı öğrenci Numarası İle Kayıt Mevcut"))
                .when(studentRules).existsStudentNo("12345678946");

        //  Kayıt yapmaya çalışırken BusinessException'ın studentRules içinden fırlatılması beklenir
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            // studentService, studentRules içindeki exitsStudentNo metodunu çağıracak
            studentService.add(createStudentRequestModel); // Kayıt yapmaya çalış
        });

        // Fırlatılan istisnanın doğru mesajı içerdiğini kontrol et
        assertEquals("Aynı öğrenci Numarası İle Kayıt Mevcut", exception.getMessage());

        // Verify: studentRules içinde exitsStudentNo metodunun çağrıldığını kontrol et
        verify(studentRules, times(1)).existsStudentNo("12345678946"); // Kural kontrolü yapıldı mı?
        verify(studentRepo, never()).save(any()); // Hiçbir kayıt yapılmamalı
    }

}

