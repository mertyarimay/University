package com.education.university.sectionServiceTest;

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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SectionServiceDeleteTest {
    @Mock
    private SectionRepo sectionRepo;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private SectionRules sectionRules;
    @InjectMocks
    private SectionServiceImpl sectionService;

    @Test
    public void sectionDeleteTest() {
        int testDeleteId = 1;
        Section section = new Section();
        section.setId(testDeleteId);
        section.setSectionName("test");

        when(sectionRepo.findById(testDeleteId)).thenReturn(Optional.of(section));

        boolean result = sectionService.delete(testDeleteId);

        assertTrue(result);
        verify(sectionRepo, times(1)).findById(testDeleteId);
        verify(sectionRepo, times(1)).deleteById(testDeleteId);

        // Silme sonrası kontrol
        when(sectionRepo.findById(testDeleteId)).thenReturn(Optional.empty());
        Optional<Section> deletedSection = sectionRepo.findById(testDeleteId);
        assertFalse(deletedSection.isPresent()); // Silinmiş olmalı
    }
    @Test
    public void sectionDeleteNotFoundTest() {
        int testDeleteId = 2;

        when(sectionRepo.findById(testDeleteId)).thenReturn(Optional.empty());

        boolean result = sectionService.delete(testDeleteId);

        assertFalse(result);  // Silme işleminin başarısız .
        verify(sectionRepo, times(1)).findById(testDeleteId);
        verify(sectionRepo, never()).deleteById(any());  // Delete çağrısının yapılmaması.
    }



}
