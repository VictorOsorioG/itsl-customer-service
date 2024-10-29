package com.globant.mentorship.itsl.customer_service.infrastucture.controller.impl;

import com.globant.mentorship.itsl.customer_service.application.dto.CustomerDto;
import com.globant.mentorship.itsl.customer_service.application.service.CustomerApplicationService;
import com.globant.mentorship.itsl.customer_service.infrastucture.controller.CustomerController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerControllerImpl implements CustomerController {

    private final String LOG_PREFIX = "Customer Controller >>>";

    private final CustomerApplicationService customerApplicationService;

    @Override
    @GetMapping("/{id}")
    public Mono<CustomerDto> getCustomerById(@PathVariable Long id) {
        log.info("{} Getting customer by id {}", LOG_PREFIX, id);
        return customerApplicationService.getCustomer(id);
    }
}
