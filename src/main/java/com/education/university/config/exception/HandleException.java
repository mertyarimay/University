package com.education.university.config.exception;


import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class HandleException {
    @ExceptionHandler
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    public ProblemDetails handleBusinessException(BusinessException businessException){
        ProblemDetails problemDetails=new ProblemDetails();
        problemDetails.setMessage(businessException.getMessage());
        return problemDetails;
    }
    @ExceptionHandler
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    public ProblemDetails validationProblems(MethodArgumentNotValidException methodArgumentNotValidException){
        ValidationProblems validationProblems=new ValidationProblems();
        validationProblems.setMessage("Validation Exception");
        validationProblems.setValidateProblems(new HashMap<>());
        for(FieldError fieldError:methodArgumentNotValidException.getBindingResult().getFieldErrors()){
            validationProblems.getValidateProblems().put(fieldError.getField(),fieldError.getDefaultMessage());

        }
        return validationProblems;
    }


}
