package com.education.university.sectionServiceTest;

import com.education.university.business.model.response.GetAllSectionResponse;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SectionServiceGetAllTest {
    @Mock
    private SectionRepo sectionRepo;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private SectionRules sectionRules;
    @InjectMocks
    private SectionServiceImpl sectionService;

    @Test
    public void sectionServiceGetAllTest() {
        Section section = new Section();
        section.setSectionName("Test");
        section.setId(1);
        Section section1 = new Section();
        section1.setSectionName("Test1");
        section1.setId(2);
        List<Section> mockSections = new ArrayList<>();
        mockSections.add(section);
        mockSections.add(section1);

        when(sectionRepo.findAll()).thenReturn(mockSections);
        when(modelMapper.map(section, GetAllSectionResponse.class)).thenReturn(new GetAllSectionResponse(1, "Test"));
        when(modelMapper.map(section1, GetAllSectionResponse.class)).thenReturn(new GetAllSectionResponse(2, "Test1"));

        List<GetAllSectionResponse> getAllSectionResponses = sectionService.getAll();


        assertNotNull(getAllSectionResponses);
        assertEquals(2, getAllSectionResponses.size());


        assertEquals(1, getAllSectionResponses.get(0).getId());
        assertEquals("Test", getAllSectionResponses.get(0).getSectionName());

        assertEquals(2, getAllSectionResponses.get(1).getId());
        assertEquals("Test1", getAllSectionResponses.get(1).getSectionName());


        verify(sectionRepo, times(1)).findAll();
        verify(modelMapper, times(1)).map(section, GetAllSectionResponse.class);
        verify(modelMapper, times(1)).map(section1, GetAllSectionResponse.class);
    }

}
