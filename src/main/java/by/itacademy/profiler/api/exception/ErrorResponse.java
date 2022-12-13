package by.itacademy.profiler.api.exception;

import java.time.LocalDateTime;

public record ErrorResponse(int statusCode, String message, LocalDateTime timeStamp) {
}

