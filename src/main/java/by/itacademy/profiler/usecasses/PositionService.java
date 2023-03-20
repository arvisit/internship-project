package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.usecasses.dto.PositionDto;

import java.util.List;

public interface PositionService {

    List<PositionDto> getPositions();

    boolean isPositionExist(Long id);
}
