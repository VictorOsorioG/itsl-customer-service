package com.globant.mentorship.itsl.customer_service.infrastucture.controller;

import com.globant.mentorship.itsl.customer_service.application.dto.CustomerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerController {
    Mono<CustomerDto> getCustomerById(Long id);
    Flux<CustomerDto> getCustomerCatalogue(Integer pageNumber, Integer pageSize);
}
