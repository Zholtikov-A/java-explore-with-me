package ru.practicum.ewmservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError badRequestException(final BadRequestException e) {
        log.warn("BadRequestException {}", e.getMessage());
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .reason("Invalid request")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError conflictRequestException(final ConflictException e) {
        log.warn("BadRequestException {}", e.getMessage());
        return ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .reason("Input data conflict")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleSqlException(final DataIntegrityViolationException e) {
        log.warn("DataIntegrityViolationException {}", e.getMessage());
        return ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .reason("Data integrity violation")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleJsonParseException(final HttpMessageNotReadableException e) {
        log.warn("HttpMessageNotReadableException {}", e.getMessage());
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .reason("Invalid JSON")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        log.warn("Validation error: {}", e.getMessage());

        String errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .reason("Request validation error")
                .message(errors)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            MissingRequestHeaderException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentTypeMismatchExceptionD(Exception e) {
        log.warn("Error {}, Description: {}", e.getClass(), e.getMessage());
        String exceptionType;
        String errorMessage;

        if (e instanceof MissingRequestHeaderException) {
            exceptionType = "MissingRequestHeaderException";
        } else if (e instanceof MissingServletRequestParameterException) {
            exceptionType = "MissingServletRequestParameterException";
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            exceptionType = "Unknown state: UNSUPPORTED_STATUS";
        } else {
            exceptionType = "Unknown exception";
        }
        errorMessage = e.getMessage();
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .reason(exceptionType)
                .message(errorMessage)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ValidationIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError validationIdException(final ValidationIdException e) {
        log.warn("ValidationIdException {}", e.getMessage());
        return ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .reason("Object not found")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError forbiddenException(final ForbiddenException e) {
        log.warn("ForbiddenException {}", e.getMessage());
        return ApiError.builder()
                .status(HttpStatus.FORBIDDEN)
                .reason("Forbidden operation.")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}


