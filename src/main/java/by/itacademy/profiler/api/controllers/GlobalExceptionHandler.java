package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.api.exception.AboutNotFoundException;
import by.itacademy.profiler.api.exception.BadRequestException;
import by.itacademy.profiler.api.exception.ContactsNotFoundException;
import by.itacademy.profiler.api.exception.CurriculumVitaeNotFoundException;
import by.itacademy.profiler.api.exception.EmptyFileException;
import by.itacademy.profiler.api.exception.ErrorResponse;
import by.itacademy.profiler.api.exception.ImageNotFoundException;
import by.itacademy.profiler.api.exception.ImageStorageException;
import by.itacademy.profiler.api.exception.UserProfileNotFoundException;
import by.itacademy.profiler.api.exception.WrongMediaTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.security.sasl.AuthenticationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String EUROPE_MINSK = "Europe/Minsk";

    @ExceptionHandler({BadRequestException.class, AuthenticationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(BadRequestException exception) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MINSK)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler({UserProfileNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerNotFoundException(UserProfileNotFoundException exception) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MINSK)));
    }

    @ExceptionHandler(CurriculumVitaeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerCurriculumVitaeNotFoundException(CurriculumVitaeNotFoundException exception) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MINSK)));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ErrorResponse handleMaxSizeException(MaxUploadSizeExceededException exception) {
        return new ErrorResponse(
                HttpStatus.EXPECTATION_FAILED.value(),
                exception.getMessage(),
                ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MINSK)));
    }

    @ExceptionHandler(WrongMediaTypeException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrorResponse handleWrongMediaTypeException(WrongMediaTypeException exception) {
        return new ErrorResponse(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                exception.getMessage(),
                ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MINSK)));
    }

    @ExceptionHandler(EmptyFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmptyFileException(EmptyFileException exception) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MINSK)));
    }

    @ExceptionHandler(ImageStorageException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleImageStorageException(ImageStorageException exception) {
        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(),
                ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MINSK)));
    }

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleImageNotFoundException(ImageNotFoundException exception) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MINSK)));
    }

    @ExceptionHandler(ContactsNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponse handleContactsNotFoundException(ContactsNotFoundException exception) {
        return new ErrorResponse(
                HttpStatus.NO_CONTENT.value(),
                exception.getMessage(),
                ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MINSK)));
    }

    @ExceptionHandler(AboutNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponse handleAboutNotFoundException(AboutNotFoundException exception) {
        return new ErrorResponse(
                HttpStatus.NO_CONTENT.value(),
                exception.getMessage(),
                ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MINSK)));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleMethodNotAllowedException(HttpRequestMethodNotSupportedException exception) {
        return new ErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                exception.getMessage(),
                ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MINSK)));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException() {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Malformed JSON request",
                ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MINSK)));
    }
}