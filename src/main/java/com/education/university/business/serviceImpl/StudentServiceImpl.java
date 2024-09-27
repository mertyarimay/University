package com.education.university.business.serviceImpl;

import com.education.university.business.model.request.CreateStudentRequestModel;
import com.education.university.business.model.request.UpdateStudentRequestModel;
import com.education.university.business.model.response.GetAllSectionIdStudentResponse;
import com.education.university.business.model.response.GetAllStudentResponse;
import com.education.university.business.model.response.GetByIdStudentResponse;
import com.education.university.business.rules.StudentRules;
import com.education.university.business.service.StudentService;
import com.education.university.entity.Student;
import com.education.university.repo.StudentRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepo studentRepo;
    private final ModelMapper modelMapper;
    private final StudentRules studentRules;

    @Override
    public CreateStudentRequestModel add(CreateStudentRequestModel createStudentRequestModel) {
        studentRules.existsStudentNo(createStudentRequestModel.getStudentNo());
        studentRules.checkSectionId(createStudentRequestModel.getSectionId());
        Student student=modelMapper.map(createStudentRequestModel,Student.class);
        studentRepo.save(student);
        CreateStudentRequestModel createStudentModel=modelMapper.map(student,CreateStudentRequestModel.class);
        return createStudentModel;
    }

    @Override
    public List<GetAllStudentResponse> getAll() {
      List<Student>students=studentRepo.findAll();
      List<GetAllStudentResponse>getAllStudentResponses=students.stream()
              .map(student -> modelMapper.map(student,GetAllStudentResponse.class))
              .collect(Collectors.toList());
      return getAllStudentResponses;
    }

    @Override
    public GetByIdStudentResponse getById(int id) {
        Student student=studentRepo.findById(id).orElse(null);
        if (student!=null){
            GetByIdStudentResponse getByIdStudentResponse=modelMapper.map(student,GetByIdStudentResponse.class);
            return getByIdStudentResponse;

        }else {
            return null;
        }
    }

    @Override
    public UpdateStudentRequestModel update(UpdateStudentRequestModel updateStudentRequestModel, int id) {
        Student student=studentRepo.findById(id).orElse(null);
        if(student!=null){
            studentRules.existsStudentNo(updateStudentRequestModel.getStudentNo());
            student.setStudentNo(updateStudentRequestModel.getStudentNo());
            studentRepo.save(student);
            UpdateStudentRequestModel updateStudentModel=modelMapper.map(student,UpdateStudentRequestModel.class);
            return updateStudentModel;
        }else {
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        Student student=studentRepo.findById(id).orElse(null);
        if (student!=null){
            studentRepo.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<GetAllSectionIdStudentResponse>getAllSectionId(Optional<Integer>sectionId) {
        if(sectionId.isPresent()){
            studentRules.checkSectionId(sectionId.get());
            List<Student>students=studentRepo.findBySectionId(sectionId.get());
            List<GetAllSectionIdStudentResponse>getAllSectionIdStudentResponses=students.stream()
                    .map(student -> modelMapper
                            .map(student,GetAllSectionIdStudentResponse.class)).collect(Collectors.toList());
            return getAllSectionIdStudentResponses;
        }else{
            return null;
        }

    }


}
