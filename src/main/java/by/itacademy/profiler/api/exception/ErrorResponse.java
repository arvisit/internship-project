package by.itacademy.profiler.api.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

    private Integer statusCode;

    private String message;

    private LocalDateTime timeStamp;

    public ErrorResponse(int statusCode, String message, LocalDateTime timeStamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.timeStamp = timeStamp;
    }
}
