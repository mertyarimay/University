package com.education.university.business.serviceImpl;

import com.education.university.business.model.request.CreateSectionRequestModel;
import com.education.university.business.model.request.UpdateSectionRequestModel;
import com.education.university.business.model.response.GetAllSectionResponse;
import com.education.university.business.model.response.GetByIdSectionResponse;
import com.education.university.business.rules.SectionRules;
import com.education.university.business.service.SectionService;
import com.education.university.entity.Section;
import com.education.university.repo.SectionRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SectionServiceImpl implements SectionService {
    private final SectionRepo sectionRepo;
    private final ModelMapper modelMapper;
    private final SectionRules sectionRules;

    @Override
    public CreateSectionRequestModel add(CreateSectionRequestModel createSectionRequestModel) {
        sectionRules.existsName(createSectionRequestModel.getSectionName());
        Section section = modelMapper.map(createSectionRequestModel, Section.class);
        sectionRepo.save(section);
        CreateSectionRequestModel createSectionModel = modelMapper.map(section, CreateSectionRequestModel.class);
        return createSectionModel;
    }

    @Override
    public List<GetAllSectionResponse> getAll() {
        List<Section> sections = sectionRepo.findAll();
        List<GetAllSectionResponse> getAllSectionResponses = sections.stream()
                .map(section -> modelMapper.map(section, GetAllSectionResponse.class))
                .collect(Collectors.toList());
        return getAllSectionResponses;
    }

    @Override
    public GetByIdSectionResponse getById(int id) {
        Section section = sectionRepo.findById(id).orElse(null);
        if (section != null) {
            GetByIdSectionResponse getByIdSectionResponse = modelMapper.map(section, GetByIdSectionResponse.class);
            return getByIdSectionResponse;
        } else {
            return null;
        }
    }

    @Override
    public UpdateSectionRequestModel update(UpdateSectionRequestModel updateSectionRequestModel, int id) {
        Optional<Section> section = sectionRepo.findById(id);
        if (section.isPresent()) {
            sectionRules.existsName(updateSectionRequestModel.getSectionName());
            section.get().setSectionName(updateSectionRequestModel.getSectionName());
            sectionRepo.save(section.get());
            UpdateSectionRequestModel updateSectionModel = modelMapper.map(section, UpdateSectionRequestModel.class);
            return updateSectionModel;
        }else {
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        Optional<Section>section=sectionRepo.findById(id);
        if(section.isPresent()){
            sectionRepo.deleteById(id);
            return true;
        }else {
            return false;
        }
    }
}

