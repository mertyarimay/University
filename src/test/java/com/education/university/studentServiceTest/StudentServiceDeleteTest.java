package com.education.university.studentServiceTest;

import com.education.university.business.rules.StudentRules;
import com.education.university.business.serviceImpl.StudentServiceImpl;
import com.education.university.entity.Section;
import com.education.university.entity.Student;
import com.education.university.repo.StudentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceDeleteTest {
    @Mock
    private StudentRepo studentRepo;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private StudentRules studentRules;
    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    public void testDeleteStudentFound() {
        int studentId = 1;
        Section section = new Section();
        section.setId(1);
        Student existingStudent = new Student(studentId, "Test", "TestLastName", "12345678946", section);


        when(studentRepo.findById(studentId)).thenReturn(Optional.of(existingStudent));


        boolean result=studentService.delete(studentId);

        assertTrue(result);
        verify(studentRepo, times(1)).findById(studentId);
        // deleteById çağrıldığını kontrol et
        verify(studentRepo, times(1)).deleteById(studentId);

        // Silme sonrası kontrol
        when(studentRepo.findById(studentId)).thenReturn(Optional.empty()); // Silindikten sonra bulunamamalı
        Optional<Student> deletedStudent = studentRepo.findById(studentId);
        assertTrue(deletedStudent.isEmpty()); // Student bulunmamalı
    }


    @Test
    public void testDeleteStudentNotFound() {
        int studentId = 1;

       //verilen ıd ile ilgili kayıt boş
        when(studentRepo.findById(studentId)).thenReturn(Optional.empty());


        boolean result = studentService.delete(studentId); // Silme işlemi


        assertFalse(result); // Sonuç false olmalı

        //  findById metodu bir kez çağrıldı mı?
        verify(studentRepo, times(1)).findById(studentId);
        // Silme işlemi yapılmamalı
        verify(studentRepo, never()).deleteById(anyInt());
    }



}
