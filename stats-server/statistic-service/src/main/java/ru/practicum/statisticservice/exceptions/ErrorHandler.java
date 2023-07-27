package ru.practicum.statisticservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiError badRequestException(final BadRequestException e) {
        log.warn("Exception badRequestException {}", e.getMessage());
        return ApiError.builder()
                .reason("Invalid date")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}


