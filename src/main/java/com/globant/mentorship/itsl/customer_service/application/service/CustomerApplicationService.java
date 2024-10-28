package com.globant.mentorship.itsl.customer_service.application.service;

import com.globant.mentorship.itsl.customer_service.application.dto.CustomerDto;
import reactor.core.publisher.Mono;

public interface CustomerApplicationService {
    Mono<CustomerDto> getCustomer(Long id);
}
