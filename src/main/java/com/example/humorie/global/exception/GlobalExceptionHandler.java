package com.example.humorie.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ErrorResponse<Object>> handleCustomException(ErrorException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse<Object> response = new ErrorResponse<>(errorCode);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}

