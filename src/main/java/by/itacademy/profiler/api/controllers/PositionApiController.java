package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.PositionService;
import by.itacademy.profiler.usecasses.dto.PositionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Position Controller", description = "API for working with positions")
@RestController
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
@Slf4j
public class PositionApiController {
    private final PositionService positionService;

    @Operation(summary = "Get list of positions")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PositionDto> getPositions() {
        List<PositionDto> positions = positionService.getPositions();
        log.debug("Getting {} positions from database", positions.size());
        return positions;
    }
}
