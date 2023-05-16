package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.CompetenceService;
import by.itacademy.profiler.usecasses.annotation.IsCvExists;
import by.itacademy.profiler.usecasses.dto.CompetenceRequestDto;
import by.itacademy.profiler.usecasses.dto.CompetenceResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Competence Controller", description = "API for working with competences")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cvs/{uuid}/competences")
@Slf4j
@Validated
public class CompetenceApiController {

    private final CompetenceService competenceService;

    @Operation(summary = "Save skills and languages in the user's cv")
    @ApiResponse(responseCode = "201", description = "CREATE")
    @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PostMapping
    public ResponseEntity<CompetenceResponseDto> add(@RequestBody @Valid CompetenceRequestDto request,
                                                     @PathVariable(name = "uuid") @IsCvExists String uuid) {
        log.debug("Input data for creating CV languages and skills: {}", request);
        CompetenceResponseDto response = competenceService.save(request, uuid);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
