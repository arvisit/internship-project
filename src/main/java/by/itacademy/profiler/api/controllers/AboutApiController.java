package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.AboutService;
import by.itacademy.profiler.usecasses.annotation.IsCvExists;
import by.itacademy.profiler.usecasses.dto.AboutDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "about", description = "the About yourself API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cvs/{uuid}/about")
@Slf4j
@Validated
public class AboutApiController {

    private final AboutService aboutService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save about yourself information", description = "Saving About section of CV", tags = {"about"})
    public ResponseEntity<AboutDto> addAbout(@PathVariable @IsCvExists String uuid, @RequestBody @Valid AboutDto aboutDto) {
        log.debug("Input data for creating about section of CV with UUID {}: {}", uuid, aboutDto);
        AboutDto saveAbout = aboutService.save(uuid, aboutDto);
        return new ResponseEntity<>(saveAbout, HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update about yourself information", description = "Update About section of CV", tags = {"about"})
    public ResponseEntity<AboutDto> updateAbout(@PathVariable @IsCvExists String uuid, @RequestBody @Valid AboutDto aboutDto) {
        log.debug("Update about section of CV {} by the data: {}", uuid, aboutDto);
        AboutDto updateAbout = aboutService.update(uuid, aboutDto);
        return new ResponseEntity<>(updateAbout, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Find about section by cv uuid", description = "Name search by %name% format", tags = {"about"})
    public ResponseEntity<AboutDto> getAbout(@PathVariable @IsCvExists String uuid){
        AboutDto about = aboutService.getAbout(uuid);
        log.debug("Getting about section of CV {} from database: {} ", uuid, about);
        return new ResponseEntity<>(about, HttpStatus.OK);
    }
}
