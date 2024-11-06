package com.globant.mentorship.itsl.customer_service.infrastucture.exception;


import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class StandardError {
    private String code;
    private LocalDateTime timestamp;
    private String description;
    private HttpStatus status;
}
