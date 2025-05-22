package com.example.scheduleappdev.exception;

import com.example.scheduleappdev.dto.res.ErrorListResDto;
import com.example.scheduleappdev.dto.res.ErrorResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorListResDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errorList = ex.getBindingResult().getFieldErrors().stream().map(
                error -> {
                    Map<String, String> errorDetail = new HashMap<>();
                    errorDetail.put("field", error.getField());
                    errorDetail.put("message", error.getDefaultMessage());
                    return errorDetail;
                }).toList();
        ErrorListResDto errorListDto = createErrorListDto(HttpStatus.BAD_REQUEST, errorList);
        return new ResponseEntity<>(errorListDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            UnauthorizedException.class,
            NotFoundException.class,
            ConflictException.class
    })
    public ResponseEntity<ErrorResDto> handleCustomExceptions(RuntimeException ex) {
        HttpStatus status = resolveStatus(ex);
        ErrorResDto errorResDto = createErrorDto(status, ex.getMessage());
        return new ResponseEntity<>(errorResDto, status);
    }

    private HttpStatus resolveStatus(RuntimeException ex) {
        if (ex instanceof UnauthorizedException) return HttpStatus.UNAUTHORIZED;
        if (ex instanceof NotFoundException) return HttpStatus.NOT_FOUND;
        if (ex instanceof ConflictException) return HttpStatus.CONFLICT;
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private ErrorResDto createErrorDto(HttpStatus status, String message) {
        return new ErrorResDto(
                status.value(),
                status.getReasonPhrase(),
                message,
                LocalDateTime.now()
        );
    }

    private ErrorListResDto createErrorListDto(HttpStatus status, List<Map<String, String>> message) {
        return new ErrorListResDto(
                status.value(),
                status.getReasonPhrase(),
                message,
                LocalDateTime.now()
        );
    }

}