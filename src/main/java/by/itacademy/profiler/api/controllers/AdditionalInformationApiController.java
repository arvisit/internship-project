package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.AdditionalInformationService;
import by.itacademy.profiler.usecasses.annotation.IsCvExists;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationRequestDto;
import by.itacademy.profiler.usecasses.dto.AdditionalInformationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Additional information controller", description = "API for working with user's additional information")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cvs/{uuid}/additional-information")
@Slf4j
@Validated
public class AdditionalInformationApiController {

    private final AdditionalInformationService additionalInformationService;

    @Operation(summary = "Save user's additional information")
    @PostMapping
    @ApiResponse(responseCode = "201", description = "CREATE")
    public ResponseEntity<AdditionalInformationResponseDto> add(@RequestBody @Valid AdditionalInformationRequestDto request,
                                                                @PathVariable(name = "uuid") @IsCvExists String uuid) {
        log.debug("Input data for saving user's additional information: {}", request);
        AdditionalInformationResponseDto response = additionalInformationService.save(request, uuid);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get user's additional information")
    @GetMapping
    @ApiResponse(responseCode = "200", description = "GET")
    public ResponseEntity<AdditionalInformationResponseDto> get(@PathVariable(name = "uuid") @IsCvExists String uuid){
        AdditionalInformationResponseDto response = additionalInformationService.getAdditionalInformationByCvUuid(uuid);
        log.debug("Getting additional information section of CV {} from database: {} ", uuid, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
