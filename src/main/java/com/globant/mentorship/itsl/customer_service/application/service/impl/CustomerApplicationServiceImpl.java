package com.globant.mentorship.itsl.customer_service.application.service.impl;

import com.globant.mentorship.itsl.customer_service.application.dto.CustomerDto;
import com.globant.mentorship.itsl.customer_service.application.service.CustomerApplicationService;
import com.globant.mentorship.itsl.customer_service.domain.model.Customer;
import com.globant.mentorship.itsl.customer_service.infrastucture.dto.CustomerRequest;
import com.globant.mentorship.itsl.customer_service.infrastucture.exception.standard_exception.CustomerNameUnique;
import com.globant.mentorship.itsl.customer_service.infrastucture.exception.standard_exception.CustomerNotFound;
import com.globant.mentorship.itsl.customer_service.infrastucture.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerApplicationServiceImpl implements CustomerApplicationService {

    private final String LOG_PREFIX = "Customer Application Service >>>";

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<CustomerDto> getCustomer(Long id) {
        log.info("{} Find customer by id {}", LOG_PREFIX, id);
        return customerRepository.findById(id)
                .map(customer -> Mono.just(
                        modelMapper.map(customer, CustomerDto.class)
                ))
                .orElse(Mono.error(CustomerNotFound::new));
    }

    @Override
    public Flux<CustomerDto> getCustomerCatalogue(Pageable pageable) {
        log.info("{} Find customers", LOG_PREFIX);
        return Flux.fromIterable(
                customerRepository.findAll(pageable).stream()
                        .map(customer -> modelMapper.map(customer, CustomerDto.class))
                        .toList()
        );
    }

    @Override
    public Mono<Void> createCustomer(CustomerRequest customerRequest) {
        checkCustomerNameUnique(customerRequest.getName());
        log.info("{} Saving customer", LOG_PREFIX);
        customerRepository.save(buildCustomer(customerRequest));
        return Mono.empty();
    }

    private void checkCustomerNameUnique(String customerName) {
        log.info("{} Checking customer name {} is unique", LOG_PREFIX, customerName);
        if (customerRepository.existsByName(customerName)) {
            throw new CustomerNameUnique();
        }
    }

    private Customer buildCustomer(CustomerRequest customerRequest) {
        return Customer.builder()
                .name(customerRequest.getName())
                .active(true)
                .build();
    }
}
