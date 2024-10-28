package com.globant.mentorship.itsl.customer_service.infrastucture.controller.impl;

import com.globant.mentorship.itsl.customer_service.application.dto.CustomerDto;
import com.globant.mentorship.itsl.customer_service.infrastucture.controller.CustomerController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerControllerImpl implements CustomerController {
    @Override
    public Mono<CustomerDto> getCustomerById(Long id) {
        return null;
    }
}
