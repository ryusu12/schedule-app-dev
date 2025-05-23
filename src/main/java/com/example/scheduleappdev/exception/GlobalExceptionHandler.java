package com.example.scheduleappdev.exception;

import com.example.scheduleappdev.dto.res.err.ErrorDetailDto;
import com.example.scheduleappdev.dto.res.err.ErrorListResDto;
import com.example.scheduleappdev.dto.res.err.ErrorResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorListResDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<ErrorDetailDto> errorDetailList = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorDetailDto(error.getField(), error.getDefaultMessage()))
                .toList();

        ErrorListResDto errorListDto = createErrorListDto(status, errorDetailList);
        return new ResponseEntity<>(errorListDto, status);
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

    private ErrorListResDto createErrorListDto(HttpStatus status, List<ErrorDetailDto> errorDetailList) {
        return new ErrorListResDto(
                status.value(),
                status.getReasonPhrase(),
                errorDetailList,
                LocalDateTime.now()
        );
    }

}