package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.SkillService;
import by.itacademy.profiler.usecasses.annotation.PositionValidation;
import by.itacademy.profiler.usecasses.dto.SkillResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Skill Controller", description = "API for working with skills")
@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
@Validated
public class SkillApiController {

    private final SkillService skillService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get a list of skills",
            description = "Get the whole list of skills or list of skills by position (enter position_id in request param)"
    )
    public ResponseEntity<List<SkillResponseDto>> getSkills(
            @RequestParam(name = "position_id", required = false)
            @Parameter(description = "position_id (optional parameter)")
            @PositionValidation Long positionId) {
        List<SkillResponseDto> skills = skillService.getSkills(positionId);
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }
}