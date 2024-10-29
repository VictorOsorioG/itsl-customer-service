package com.globant.mentorship.itsl.customer_service.infrastucture.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor {

    private final String LOG_PREFIX = "Controller Advisor >>> Exception caught in";

    @ExceptionHandler(StandardException.class)
    public ResponseEntity<StandardError> handlerStandardException(StandardException standardException) {
        log.info("{} handleStandardException : {}", LOG_PREFIX, standardException.getStandardError().getDescription());
        return ResponseEntity.status(standardException.getHttpStatus())
                .body(standardException.getStandardError());
    }
}
