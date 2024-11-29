package com.globant.mentorship.itsl.customer_service.infrastucture.exception.standard_exception;

import com.globant.mentorship.itsl.customer_service.infrastucture.exception.StandardException;
import org.springframework.http.HttpStatus;

public class CustomerNameUnique extends StandardException {
    public CustomerNameUnique() {
        super("E002", HttpStatus.CONFLICT, "Customer already exists with provided data");
    }
}
