package com.api.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorHandle> handle(MethodArgumentNotValidException exception) {

        List<FieldError> fieldErrorList = exception.getBindingResult().getFieldErrors();

        List<ErrorHandle> errorHandleList = new ArrayList<>();

        fieldErrorList.forEach(error -> {
            errorHandleList.add(new ErrorHandle(error.getField(), error.getDefaultMessage()));
        });

        return errorHandleList;
    }

}
