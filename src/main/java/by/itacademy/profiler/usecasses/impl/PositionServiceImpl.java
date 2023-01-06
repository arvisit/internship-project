package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.Position;
import by.itacademy.profiler.persistence.repository.PositionRepository;
import by.itacademy.profiler.usecasses.PositionService;
import by.itacademy.profiler.usecasses.dto.PositionDto;
import by.itacademy.profiler.usecasses.mapper.PositionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PositionServiceImpl implements PositionService {

    public static final String POSITION_ID = "id";
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    @Override
    public List<PositionDto> getPositions() {
        List<Position> positions = positionRepository.findAll(Sort.by(Sort.Order.asc(POSITION_ID)));
        return positionMapper.toListDto(positions);
    }
}
