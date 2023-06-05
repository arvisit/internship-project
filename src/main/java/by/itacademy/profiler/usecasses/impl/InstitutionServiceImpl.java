package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.Institution;
import by.itacademy.profiler.persistence.repository.InstitutionRepository;
import by.itacademy.profiler.usecasses.InstitutionService;
import by.itacademy.profiler.usecasses.dto.InstitutionResponseDto;
import by.itacademy.profiler.usecasses.mapper.InstitutionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

    @Override
    public List<InstitutionResponseDto> getInstitutions() {
        List<Institution> institutions = institutionRepository.findAllByOrderByName();

        return institutions.stream()
                .map(institutionMapper::fromEntityToDto)
                .toList();
    }
}
