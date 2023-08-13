package com.api.management.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.lang.IllegalArgumentException;

@RestControllerAdvice
public class ControllerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<StandardError>> MethodArgumentNotValidHandlerMethod(MethodArgumentNotValidException exception, HttpServletRequest request) {
        List<FieldError> fieldErrorList = exception.getBindingResult().getFieldErrors();
        List<StandardError> errorHandleList = new ArrayList<>();

        fieldErrorList.forEach(error -> {
            errorHandleList.add(new StandardError(LocalDateTime.now(), 400, "Bad Request", error.getDefaultMessage(), request.getRequestURI()));
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorHandleList);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> HttpMessageNotReadableHandlerMethod(HttpMessageNotReadableException exception, HttpServletRequest request) {
        StandardError se = new StandardError(LocalDateTime.now(), 400, "Bad Request", exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(se);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> IllegalArgumentExceptionHandlerMethod(IllegalArgumentException exception, HttpServletRequest request) {
        StandardError se = new StandardError(LocalDateTime.now(), 400, "Bad Request", exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(se);
    }

}
