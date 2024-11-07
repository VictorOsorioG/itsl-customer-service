package com.globant.mentorship.itsl.customer_service.application.service.impl;

import com.globant.mentorship.itsl.customer_service.application.dto.CustomerDto;
import com.globant.mentorship.itsl.customer_service.application.service.CustomerApplicationService;
import com.globant.mentorship.itsl.customer_service.infrastucture.exception.standard_exception.CustomerNotFound;
import com.globant.mentorship.itsl.customer_service.infrastucture.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
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
}
