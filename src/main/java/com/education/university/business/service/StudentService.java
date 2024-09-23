package com.education.university.business.service;

import com.education.university.business.model.request.CreateStudentRequestModel;
import com.education.university.business.model.request.UpdateStudentRequestModel;
import com.education.university.business.model.response.GetAllSectionIdStudentResponse;
import com.education.university.business.model.response.GetAllStudentResponse;
import com.education.university.business.model.response.GetByIdStudentResponse;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    CreateStudentRequestModel add(CreateStudentRequestModel createStudentRequestModel);
    List<GetAllStudentResponse> getAll();
    GetByIdStudentResponse getById(int id);
    UpdateStudentRequestModel update(UpdateStudentRequestModel updateStudentRequestModel,int id);
    boolean delete(int id);
    List<GetAllSectionIdStudentResponse> getAllSectionId(Optional<Integer> sectionId);
}
