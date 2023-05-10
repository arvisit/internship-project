package by.itacademy.profiler.api.controllers;


import by.itacademy.profiler.usecasses.LanguageService;
import by.itacademy.profiler.usecasses.dto.LanguageResponseDto;
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

@Tag(name = "Language Controller", description = "API for working with languages")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/languages")
public class LanguageApiController {

    private final LanguageService languageService;

    @Operation(summary = "Get all languages list")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = LanguageResponseDto.class))))
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @GetMapping
    public ResponseEntity<List<LanguageResponseDto>> getAllLanguages() {
        List<LanguageResponseDto> allLanguages = languageService.getLanguages();
        return new ResponseEntity<>(allLanguages, HttpStatus.OK);
    }
}
