package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.Position;
import by.itacademy.profiler.persistence.repository.PositionRepository;
import by.itacademy.profiler.usecasses.dto.PositionDto;
import by.itacademy.profiler.usecasses.mapper.PositionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;

import static by.itacademy.profiler.usecasses.impl.PositionServiceImpl.SORTING_PROPERTY;
import static by.itacademy.profiler.util.PositionTestData.createPosition;
import static by.itacademy.profiler.util.PositionTestData.createPositionDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PositionServiceImplTest {

    @InjectMocks
    private PositionServiceImpl positionServiceImpl;

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private PositionMapper positionMapper;

    @Test
    void shouldReturnAllPositionsWhenInvokeGetPositions() {
        Position position = createPosition().build();
        PositionDto positionDto = createPositionDto().build();

        when(positionRepository.findAll(Sort.by(Sort.Order.asc(SORTING_PROPERTY)))).thenReturn(List.of(position));
        when(positionMapper.toListDto(List.of(position))).thenReturn(List.of(positionDto));

        List<PositionDto> positions = positionServiceImpl.getPositions();
        assertEquals(1, positions.size());
    }
}
