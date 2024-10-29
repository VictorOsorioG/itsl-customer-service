package com.globant.mentorship.itsl.customer_service.infrastucture.exception.standard_exception;

import com.globant.mentorship.itsl.customer_service.infrastucture.exception.StandardException;
import org.springframework.http.HttpStatus;

public class CustomerNotFound extends StandardException {
    public CustomerNotFound() {
        super("E001", HttpStatus.BAD_REQUEST, "Customer not found with provided data");
    }
}
