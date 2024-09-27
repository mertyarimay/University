package com.education.university.sectionServiceTest;

import com.education.university.business.model.response.GetByIdSectionResponse;
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
public class SectionServiceGetByIdTest {
    @Mock
    private SectionRepo sectionRepo;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private SectionRules sectionRules;
    @InjectMocks
    private SectionServiceImpl sectionService;

    @Test
    public void getByIdTest(){
        int testId=1;
        Section section=new Section();
        section.setId(1);
        section.setSectionName("Test");
        GetByIdSectionResponse getByIdSectionResponse=new GetByIdSectionResponse(1,"Test");
          when(sectionRepo.findById(testId)).thenReturn(Optional.of(section));
          when(modelMapper.map(section,GetByIdSectionResponse.class)).thenReturn(getByIdSectionResponse);



        GetByIdSectionResponse getByIdSectionTest=sectionService.getById(testId);

        assertNotNull(getByIdSectionTest);
        assertEquals(1,getByIdSectionTest.getId());
        assertEquals("Test",getByIdSectionTest.getSectionName());

        verify(sectionRepo,times(1)).findById(testId);
        verify(modelMapper,times(1)).map(section,GetByIdSectionResponse.class);

    }
    @Test
    public void getByIdNotFoundTest(){
      int testId=2;

      when(sectionRepo.findById(testId)).thenReturn(Optional.empty());

      GetByIdSectionResponse getByIdSectionTest=sectionService.getById(testId);

      assertNull(getByIdSectionTest);

      verify(sectionRepo,times(1)).findById(testId);
      verify(modelMapper,never()).map(any(),any());


    }

}
