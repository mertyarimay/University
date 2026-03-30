package com.education.university.sectionServiceTest;

import com.education.university.business.model.request.CreateSectionRequestModel;
import com.education.university.business.rules.SectionRules;
import com.education.university.business.serviceImpl.SectionServiceImpl;
import com.education.university.config.exception.BusinessException;
import com.education.university.entity.Section;
import com.education.university.repo.SectionRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SectionServiceAddTest {
    @Mock
    private SectionRepo sectionRepo;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private  SectionRules sectionRules;
    @InjectMocks
    private SectionServiceImpl sectionService;

    @Test
    public void sectionAddTest(){
        CreateSectionRequestModel createSectionRequestModel=new CreateSectionRequestModel("Test");
        Section section=new Section();
        section.setSectionName("Test");



       when(modelMapper.map(createSectionRequestModel,Section.class)).thenReturn(section);
       when(sectionRepo.save(section)).thenReturn(section);
       when(modelMapper.map(section, CreateSectionRequestModel.class)).thenReturn(createSectionRequestModel);

        CreateSectionRequestModel createSectionModel=sectionService.add(createSectionRequestModel);

        assertNotNull(createSectionModel);
        assertEquals("Test",createSectionModel.getSectionName());

        verify(modelMapper, times(1)).map(createSectionRequestModel, Section.class);
        verify(modelMapper,times(1)).map(section, CreateSectionRequestModel.class);
        verify(sectionRepo,times(1)).save(section);
    }

    @Test
    public void checkSectionName() {

        CreateSectionRequestModel createSectionRequestModel = new CreateSectionRequestModel();
        createSectionRequestModel.setSectionName("Test");

        // rule exception fırlatacak
        doThrow(new BusinessException("Aynı isimde kayıt mevcuttur"))
                .when(sectionRules)
                .existsName(createSectionRequestModel.getSectionName());

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            sectionService.add(createSectionRequestModel);
        });

        // then
        assertEquals("Aynı isimde kayıt mevcuttur", exception.getMessage());

        verify(sectionRules, times(1))
                .existsName(createSectionRequestModel.getSectionName());

        // exception olduğu için save çağrılmamalı
        verify(sectionRepo, never()).save(any());
    }

    }





