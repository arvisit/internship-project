package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.Sphere;
import by.itacademy.profiler.persistence.repository.SphereRepository;
import by.itacademy.profiler.usecasses.SphereService;
import by.itacademy.profiler.usecasses.dto.SphereResponseDto;
import by.itacademy.profiler.usecasses.mapper.SphereMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SphereServiceImpl implements SphereService {

    private final SphereRepository sphereRepository;
    private final SphereMapper sphereMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SphereResponseDto> getSpheres() {
        List<Sphere> spheres = sphereRepository.findAllByOrderById();

        return spheres.stream()
                .map(sphereMapper::fromEntityToDto)
                .toList();
    }
}
