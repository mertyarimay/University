package com.education.university.studentServiceTest;

import com.education.university.business.model.response.GetByIdStudentResponse;
import com.education.university.business.rules.StudentRules;
import com.education.university.business.serviceImpl.StudentServiceImpl;

import com.education.university.entity.Section;
import com.education.university.entity.Student;
import com.education.university.repo.StudentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceGetByIdTest {
    @Mock
    private StudentRepo studentRepo; // Mock Repository  repoyu taklit ediyor sahte nesne olarak tanımlanıyor

    @Mock
    private ModelMapper modelMapper; // Mock ModelMapper  model mapperı taklit ediyor
    @Mock
    private StudentRules studentRules;
    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }



   @Test
    public void testGetById() {
       Section section = new Section();
       section.setId(1);
       section.setSectionName("TestSection");
       Student student = new Student(1, "Test", "TestLastName", "12345678978", section);
       GetByIdStudentResponse getByIdStudentResponse = new GetByIdStudentResponse("Test", "TestLastName", "12345678978", "TestSection");

       when(studentRepo.findById(1)).thenReturn(Optional.of(student));
       when(modelMapper.map(student, GetByIdStudentResponse.class)).thenReturn(getByIdStudentResponse);

       GetByIdStudentResponse getByIdStudent = studentService.getById(1);


       assertNotNull(getByIdStudent);
       assertEquals("Test", getByIdStudent.getName());
       assertEquals("TestLastName", getByIdStudent.getLastName());
       assertEquals("12345678978", getByIdStudent.getStudentNo());
       assertEquals("TestSection", getByIdStudent.getSectionName());

       // Doğru metotların çağrıldığını kontrol et
       verify(studentRepo, times(1)).findById(1);
       verify(modelMapper, times(1)).map(student, GetByIdStudentResponse.class);
   }

   //ıd ye ait kayıt olmama durumu
   @Test
   public void testGetByIdNotFound() {

       when(studentRepo.findById(1)).thenReturn(Optional.empty()); // ID bulunamadığında boş döner


       GetByIdStudentResponse response = studentService.getById(1); // Bu durumda response null olmalı

       // Assert  burda servicten gelen boş geliyorsa zaten metodum doğru çalışıyor demektir
       assertNull(response);


       verify(studentRepo, times(1)).findById(1);
       verify(modelMapper, never()).map(any(), any()); // ModelMapper map metodu çağrılmamalı
   }

}

