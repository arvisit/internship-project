package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.InstitutionService;
import by.itacademy.profiler.usecasses.dto.InstitutionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Institution Controller", description = "API for working with Institution")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/institutions")
public class InstitutionApiController {

    private final InstitutionService institutionService;

    @Operation(summary = "Get all institutions list")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = InstitutionResponseDto.class))))
    @GetMapping
    public ResponseEntity<List<InstitutionResponseDto>> getAllInstitutions() {
        List<InstitutionResponseDto> institutions = institutionService.getInstitutions();
        return new ResponseEntity<>(institutions, HttpStatus.OK);
    }
}
