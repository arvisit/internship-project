package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.PositionService;
import by.itacademy.profiler.usecasses.dto.PositionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
@Slf4j
public class PositionApiController {
    private final PositionService positionService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PositionDto> getPositions() {
        List<PositionDto> positions = positionService.getPositions();
        log.debug("Getting {} positions from database", positions.size());
        return positions;
    }
}
