package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.api.exception.BadRequestException;
import by.itacademy.profiler.usecasses.CurriculumVitaeService;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeRequestDto;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cvs")
@Slf4j
public class CurriculumVitaeApiController {

    private final CurriculumVitaeService curriculumVitaeService;

    @PostMapping
    public ResponseEntity<CurriculumVitaeResponseDto> save(@RequestBody @Valid CurriculumVitaeRequestDto curriculumVitaeRequestDto) {
        if (curriculumVitaeService.isCreationCvAvailable()) {
            log.debug("Input data for creating CV: {} ", curriculumVitaeRequestDto);
            CurriculumVitaeResponseDto responseResume = curriculumVitaeService.save(curriculumVitaeRequestDto);
            return new ResponseEntity<>(responseResume, HttpStatus.CREATED);
        }
        throw new BadRequestException("Curriculum vitae creation limit exceeded");
    }
}
