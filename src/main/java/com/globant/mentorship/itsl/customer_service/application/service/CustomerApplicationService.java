package com.globant.mentorship.itsl.customer_service.application.service;

import com.globant.mentorship.itsl.customer_service.application.dto.CustomerDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerApplicationService {
    Mono<CustomerDto> getCustomer(Long id);

    Flux<CustomerDto> getCustomerCatalogue(Pageable pageable);
}

