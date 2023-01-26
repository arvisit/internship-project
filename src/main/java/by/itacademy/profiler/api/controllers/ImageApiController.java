package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.api.exception.BadRequestException;
import by.itacademy.profiler.usecasses.ImageService;
import by.itacademy.profiler.usecasses.dto.ImageDto;
import by.itacademy.profiler.usecasses.util.AuthUtil;
import by.itacademy.profiler.usecasses.util.ValidateImage;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
    public ResponseEntity<ImageDto> uploadImage(@NotNull MultipartFile image) {
        ValidateImage.validate(image);
        String username = AuthUtil.getUsername();
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
}
