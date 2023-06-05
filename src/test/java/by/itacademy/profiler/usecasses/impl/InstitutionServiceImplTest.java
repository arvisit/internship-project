package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.Institution;
import by.itacademy.profiler.persistence.repository.InstitutionRepository;
import by.itacademy.profiler.usecasses.dto.InstitutionResponseDto;
import by.itacademy.profiler.usecasses.mapper.InstitutionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static by.itacademy.profiler.util.InstitutionsTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstitutionServiceImplTest {

    @InjectMocks
    private InstitutionServiceImpl institutionService;

    @Mock
    private InstitutionRepository institutionRepository;

    @Mock
    private InstitutionMapper institutionMapper;

    @Test
    void shouldReturnNotEmptyInstitutionListWhenGettingInstitutions() {
        Institution institution = createInstitution().build();
        InstitutionResponseDto institutionResponseDto = createInstitutionResponseDto().build();
        int expectedListSize = 1;

        when(institutionMapper.fromEntityToDto(institution)).thenReturn(institutionResponseDto);
        when(institutionRepository.findAllByOrderByName()).thenReturn(List.of(institution));

        List<InstitutionResponseDto> actualResult = institutionService.getInstitutions();
        assertEquals(expectedListSize,actualResult.size());
    }
}