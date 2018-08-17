package com.example.demo.controller;

import com.example.demo.ServiceException;
import com.example.demo.contract.ErrorResponse;
import com.example.demo.ServiceError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class DemoServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<Object> handleServiceException(final ServiceException ex,
                                                               final WebRequest webRequest) {
        ServiceError serviceError = ex.serviceError();
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                serviceError.code(),
                serviceError.message());
        HttpHeaders httpHeaders = new HttpHeaders();
        return handleExceptionInternal(ex, errorResponse, httpHeaders, serviceError.httpStatus(), webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders httpHeaders,
                                                                  final HttpStatus httpStatus,
                                                                  final WebRequest webRequest) {
        ServiceError serviceError = ServiceError.METHOD_ARGUMENT_NOT_VALID;
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),
                serviceError.code(),
                ex.getBindingResult().toString());
        return handleExceptionInternal(ex, errorResponse, httpHeaders, httpStatus, webRequest);
    }

}
