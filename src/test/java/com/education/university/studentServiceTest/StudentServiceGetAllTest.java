package com.education.university.studentServiceTest;

import com.education.university.business.model.response.GetAllStudentResponse;
import com.education.university.business.rules.StudentRules;
import com.education.university.business.serviceImpl.StudentServiceImpl;
import com.education.university.entity.Student;
import com.education.university.repo.StudentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class StudentServiceGetAllTest {
    @Mock
    private StudentRepo studentRepo; // Mock Repository  repoyu taklit ediyor sahte nesne olarak tanımlanıyor

    @Mock
    private ModelMapper modelMapper; // Mock ModelMapper  model mapperı taklit ediyor
    @Mock
    private StudentRules studentRules;

    @InjectMocks
    private StudentServiceImpl studentService; // @InjectMocks: studentService nesnesinin içerisine mock nesneleri enjekte eder. Böylece test edilen sınıf, test ortamında çalışabilir.

    @BeforeEach //Her test metodundan önce çalışacak olan bir metot. Burada, Mockito'nun mock nesnelerini başlatıyoruz.
    public void setUp() {      // Mock nesneleri başlatmak için kullanılır
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllStudents() {
        // Mock veriler test için 2 adet test nesnesi oluşturuluyor   bu kısım aslında repoyu taklit ediyor
        Student student1 = new Student(1, "John", "Doe", "12345", null);
        Student student2 = new Student(2, "Jane", "Smith", "67890", null);

        List<Student> mockStudents = Arrays.asList(student1, student2);   //oluşturulan student nesnelerini liste atıyoruz
        when(studentRepo.findAll()).thenReturn(mockStudents); // Repo'dan find all metodu çağrıldığı zaman oluşturduğumuz listi döndürüyoruz

        // ModelMapper için mock dönüşümleri ayarlama her model mapper mapi çağrıldığında bunu yap  bu kısım service taklit ediyor
        when(modelMapper.map(student1, GetAllStudentResponse.class))
                .thenReturn(new GetAllStudentResponse("John", "Doe", "12345", null));
        when(modelMapper.map(student2, GetAllStudentResponse.class))
                .thenReturn(new GetAllStudentResponse("Jane", "Smith", "67890", null));

        // Servis metodunu çağır  bu kısımda api  taklit ediyor
        List<GetAllStudentResponse> response = studentService.getAll();

        // Sonuçları doğrula
        assertNotNull(response);   //listenin boş olmadığını kontrol eder
        assertEquals(2, response.size());
        assertEquals("John", response.get(0).getName());   //beklenen değerle gerçek değeri karşılaştırır
        assertEquals("Doe", response.get(0).getLastName());
        assertEquals("12345", response.get(0).getStudentNo());
        assertEquals("Jane", response.get(1).getName());
        assertEquals("Smith", response.get(1).getLastName());
        assertEquals("67890", response.get(1).getStudentNo());

        // Mock'un çağrıldığını doğrula verify(...): Mock nesnelerinin beklenen sayıda çağrılıp çağrılmadığını kontrol eder. findAll ve map metodlarının bir kez çağrıldığını doğruluyoruz.
        verify(studentRepo, times(1)).findAll();
        verify(modelMapper, times(1)).map(student1, GetAllStudentResponse.class);
        verify(modelMapper, times(1)).map(student2, GetAllStudentResponse.class);
    }
}

