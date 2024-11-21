package com.globant.mentorship.itsl.customer_service.infrastucture.controller.impl;

import com.globant.mentorship.itsl.customer_service.application.dto.CustomerDto;
import com.globant.mentorship.itsl.customer_service.application.service.CustomerApplicationService;
import com.globant.mentorship.itsl.customer_service.infrastucture.controller.CustomerController;
import com.globant.mentorship.itsl.customer_service.infrastucture.dto.CustomerRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
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

    @Override
    @GetMapping("/catalogue")
    public Flux<CustomerDto> getCustomerCatalogue(
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize
    ) {
        log.info("{} Getting customer catalogue", LOG_PREFIX);
        return customerApplicationService.getCustomerCatalogue(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createCustomer(@Valid CustomerRequest customerRequest) {
        log.info("{} Creating customer", LOG_PREFIX);
        return customerApplicationService.createCustomer(customerRequest);
    }
}
