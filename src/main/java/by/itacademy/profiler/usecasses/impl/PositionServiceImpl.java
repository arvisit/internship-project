package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.Position;
import by.itacademy.profiler.persistence.repository.PositionRepository;
import by.itacademy.profiler.usecasses.PositionService;
import by.itacademy.profiler.usecasses.dto.PositionDto;
import by.itacademy.profiler.usecasses.mapper.PositionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    @Override
    public List<PositionDto> getPositions() {
        List<Position> positions = positionRepository.findAll();
        return positionMapper.toListDto(positions);
    }
}
