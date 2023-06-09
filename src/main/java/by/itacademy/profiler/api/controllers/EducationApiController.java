package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.EducationService;
import by.itacademy.profiler.usecasses.annotation.IsCvExists;
import by.itacademy.profiler.usecasses.dto.EducationRequestDto;
import by.itacademy.profiler.usecasses.dto.EducationResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Education Controller", description = "API for working with education")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cvs/{uuid}/educations")
@Slf4j
@Validated
public class EducationApiController {

    private final EducationService educationService;

    @Operation(summary = "Save lists of main educations and courses in the user's cv")
    @ApiResponse(responseCode = "201", description = "CREATE",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = EducationResponseDto.class))))
    @PostMapping
    public ResponseEntity<EducationResponseDto> add(@RequestBody @Valid EducationRequestDto request,
            @PathVariable(name = "uuid") @IsCvExists String uuid) {
        log.debug("Input data for saving CV main educations and courses: {}", request);
        EducationResponseDto response = educationService.save(request, uuid);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
