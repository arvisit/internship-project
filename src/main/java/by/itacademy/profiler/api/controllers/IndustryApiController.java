package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.IndustryService;
import by.itacademy.profiler.usecasses.dto.IndustryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Industry Controller", description = "API for working with industries")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/industries")
public class IndustryApiController {

    private final IndustryService industryService;

    @Operation(summary = "Get all industries")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = IndustryResponseDto.class))))
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED")
    @GetMapping
    public ResponseEntity<List<IndustryResponseDto>> get() {
        List<IndustryResponseDto> industries = industryService.getIndustries();
        return ResponseEntity.ok(industries);
    }

}
