package com.globant.mentorship.itsl.customer_service.infrastucture.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(StandardException.class)
    public ResponseEntity<StandardError> handlerStandardException(StandardException standardException) {
        return ResponseEntity.status(standardException.getHttpStatus())
                .body(standardException.getStandardError());
    }
}
