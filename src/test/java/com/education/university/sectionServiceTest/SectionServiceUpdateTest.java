package com.education.university.sectionServiceTest;

import com.education.university.business.model.request.UpdateSectionRequestModel;


import com.education.university.business.rules.SectionRules;
import com.education.university.business.serviceImpl.SectionServiceImpl;
import com.education.university.entity.Section;
import com.education.university.repo.SectionRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SectionServiceUpdateTest {
    @Mock
    private SectionRepo sectionRepo;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private SectionRules sectionRules;
    @InjectMocks
    private SectionServiceImpl sectionService;

    @Test
    public void sectionUpdateTest() {
        int testId = 1;
        UpdateSectionRequestModel updateSectionRequestModel = new UpdateSectionRequestModel("testUpdate");
        //mevcut bölüm
        Section section = new Section();
        section.setId(1);
        section.setSectionName("test");
       //güncellenmiş Bölüm
        Section updatedSection = new Section();
        updatedSection.setId(testId);
        updatedSection.setSectionName(updateSectionRequestModel.getSectionName());


        when(sectionRepo.findById(testId)).thenReturn(Optional.of(section));
        when(sectionRepo.save(updatedSection)).thenReturn(updatedSection);
        when(modelMapper.map(updatedSection, UpdateSectionRequestModel.class)).thenReturn(updateSectionRequestModel);


        UpdateSectionRequestModel result = sectionService.update(updateSectionRequestModel, testId);


        assertNotNull(result);
        assertEquals(updateSectionRequestModel.getSectionName(), result.getSectionName());

        verify(sectionRepo, times(1)).findById(testId);
        verify(sectionRepo, times(1)).save(updatedSection);
        verify(modelMapper, times(1)).map(updatedSection, UpdateSectionRequestModel.class);
    }
    @Test
    public void sectionUpdateNotFoundTest(){
        int testId=2;
        UpdateSectionRequestModel updateSectionRequestModel = new UpdateSectionRequestModel("testUpdate");

        when(sectionRepo.findById(testId)).thenReturn(Optional.empty());

        UpdateSectionRequestModel updateSectionRequest=sectionService.update(updateSectionRequestModel,testId);


        assertNull(updateSectionRequest);

        verify(sectionRepo,times(1)).findById(testId);
        verify(sectionRepo,never()).save(any());
    }




}
