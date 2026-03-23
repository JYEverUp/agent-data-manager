package com.alibaba.cloud.ai.agentdatamanager.controller;

import com.alibaba.cloud.ai.agentdatamanager.exception.BusinessException;
import com.alibaba.cloud.ai.agentdatamanager.vo.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<String> handleBusiness(BusinessException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleValidation(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "request validation failed";
        return ApiResponse.error(message);
    }

    @ExceptionHandler({ ConstraintViolationException.class, HttpMessageNotReadableException.class })
    public ApiResponse<String> handleBadRequest(Exception ex) {
        return ApiResponse.error("request is invalid");
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleOther(Exception ex) {
        return ApiResponse.error("server error: " + ex.getMessage());
    }

}
