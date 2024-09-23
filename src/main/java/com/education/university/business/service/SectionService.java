package com.education.university.business.service;

import com.education.university.business.model.request.CreateSectionRequestModel;
import com.education.university.business.model.request.UpdateSectionRequestModel;
import com.education.university.business.model.response.GetAllSectionResponse;
import com.education.university.business.model.response.GetByIdSectionResponse;

import java.util.List;

public interface SectionService {

    CreateSectionRequestModel add(CreateSectionRequestModel createSectionRequestModel);
    List<GetAllSectionResponse>getAll();
    GetByIdSectionResponse getById(int id);
    UpdateSectionRequestModel update(UpdateSectionRequestModel updateSectionRequestModel,int id);
    boolean delete(int id);
}
