package com.education.university.studentServiceTest;


import com.education.university.business.model.request.UpdateStudentRequestModel;
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

public class StudentServiceUpdateTest {
    @Mock
    private StudentRepo studentRepo;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private StudentRules studentRules;
    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateStudent() {
        int studentId = 1;
        UpdateStudentRequestModel updateStudentRequestModel = new UpdateStudentRequestModel("15896312548");

        Section section = new Section();
        section.setId(1);
        // Mevcut öğrenci
        Student existingStudent = new Student(studentId, "Test", "TestLastName", "12345678946", section);

       //güncellenmiş öğrenci
        Student updatedStudent = new Student(studentId, "Test", "TestLastName", updateStudentRequestModel.getStudentNo(), section);


        when(studentRepo.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepo.save(updatedStudent)).thenReturn(updatedStudent);
        when(modelMapper.map(updatedStudent, UpdateStudentRequestModel.class)).thenReturn(updateStudentRequestModel);

        UpdateStudentRequestModel updatedResponse = studentService.update(updateStudentRequestModel, studentId);

        assertNotNull(updatedResponse);
        // Güncellenmiş öğrenci numarasını kontrol et
        assertEquals(updateStudentRequestModel.getStudentNo(), updatedResponse.getStudentNo());


        verify(studentRepo, times(1)).findById(studentId); // Mevcut öğrenci sorgulandı mı?
        verify(studentRepo, times(1)).save(updatedStudent); // Güncellenmiş öğrenci kaydedildi mi?
        verify(modelMapper, times(1)).map(updatedStudent, UpdateStudentRequestModel.class); // ModelMapper map metodu çağrıldı mı?
    }

    //Güncellenmek istenen ıd boş
    @Test
    public void testUpdateStudentNotFound() {
        int studentId = 1;
        UpdateStudentRequestModel updateStudentRequestModel = new UpdateStudentRequestModel("15896312548");

        when(studentRepo.findById(studentId)).thenReturn(Optional.empty()); // ID bulunamadığında boş döner


        UpdateStudentRequestModel updatedResponse = studentService.update(updateStudentRequestModel, studentId); // Güncelleme işlemi


        assertNull(updatedResponse);


        verify(studentRepo, times(1)).findById(studentId);
        verify(studentRepo, never()).save(any());
    }


}




