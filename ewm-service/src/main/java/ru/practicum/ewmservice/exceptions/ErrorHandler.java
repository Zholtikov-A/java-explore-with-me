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

/**
 * Класс описывающий ErrorHandler для централизованной обработки ошибок.
 */

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError badRequestException(final BadRequestException e) {
        log.warn("Исключение badRequestException {}", e.getMessage());
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .reason("Запрос составлен некорректно")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError conflictRequestException(final ConflictException e) {
        log.warn("Исключение badRequestException {}", e.getMessage());
        return ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .reason("Входящие данные имеют конфликт")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleSqlException(final DataIntegrityViolationException e) {
        log.warn("Ошибка DataIntegrityViolationException {}", e.getMessage());
        return ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .reason("Нарушение целостности данных")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleJsonParseException(final HttpMessageNotReadableException e) {
        log.warn("Ошибка HttpMessageNotReadableException {}", e.getMessage());
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .reason("Некорректный JSON запрос")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        log.warn("Ошибка валидации: {}", e.getMessage());

        String errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .reason("Ошибка валидации данных запроса")
                .message(errors)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            MissingRequestHeaderException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentTypeMismatchExceptionD(Exception e) {
        log.warn("Ошибка {}, описание: {}", e.getClass(), e.getMessage());
        String exceptionType;
        String errorMessage;

        if (e instanceof MissingRequestHeaderException) {
            exceptionType = "MissingRequestHeaderException";
        } else if (e instanceof MissingServletRequestParameterException) {
            exceptionType = "MissingServletRequestParameterException";
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            exceptionType = "Unknown state: UNSUPPORTED_STATUS";
        } else {
            exceptionType = "Неизвестное исключение";
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
        log.warn("Исключение ValidationIdException {}", e.getMessage());
        return ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .reason("The required object was not found")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError forbiddenException(final ForbiddenException e) {
        log.warn("Исключение ValidationDataTimeException {}", e.getMessage());
        return ApiError.builder()
                .status(HttpStatus.FORBIDDEN)
                .reason("Для запрошенной операции условия не выполнены.")
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}


