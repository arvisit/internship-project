package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.api.exception.BadRequestException;
import by.itacademy.profiler.usecasses.ImageService;
import by.itacademy.profiler.usecasses.dto.ImageDto;
import by.itacademy.profiler.usecasses.util.AuthService;
import by.itacademy.profiler.usecasses.util.ValidateImage;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/images")
public class ImageApiController {

    private final ImageService imageService;

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ImageDto> uploadImage(@NotNull MultipartFile image) {
        ValidateImage.validate(image);
        String username = authService.getUsername();
        log.debug("Received file with contentType: {}, file name is: {}, file size is: {}, from user: {}",
                image.getContentType(),
                image.getOriginalFilename(),
                image.getSize(),
                username);
        try {
            ImageDto imageDto = imageService.storageImage(image.getInputStream(), username);
            return new ResponseEntity<>(imageDto, HttpStatus.CREATED);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ImageDto> replaceImage(@NotNull MultipartFile image, @PathVariable String uuid) {
        ValidateImage.validate(image);
        String username = authService.getUsername();
        log.debug("Received file with contentType: {}, file name is: {}, file size is: {}, from user: {}",
                image.getContentType(),
                image.getOriginalFilename(),
                image.getSize(),
                username);
        try {
            ImageDto imageDto = imageService.replaceImage(image.getInputStream(), uuid);
            return new ResponseEntity<>(imageDto, HttpStatus.OK);
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @GetMapping(value = "/{uuid}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String uuid) {
        String username = authService.getUsername();
        try {
            byte[] image = imageService.getImage(uuid);
            log.debug("User {} is successful download image with uuid {}", username, uuid);
            return new ResponseEntity<>(image, HttpStatus.OK);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
}
