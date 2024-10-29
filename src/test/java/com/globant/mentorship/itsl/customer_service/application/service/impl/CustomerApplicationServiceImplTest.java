package com.globant.mentorship.itsl.customer_service.application.service.impl;

import com.globant.mentorship.itsl.customer_service.application.dto.CustomerDto;
import com.globant.mentorship.itsl.customer_service.domain.model.Customer;
import com.globant.mentorship.itsl.customer_service.infrastucture.exception.standard_exception.CustomerNotFound;
import com.globant.mentorship.itsl.customer_service.infrastucture.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerApplicationServiceImplTest {

    @InjectMocks
    private CustomerApplicationServiceImpl customerApplicationService;
    @Mock
    private CustomerRepository customerRepository;
    @Spy
    private ModelMapper modelMapper;


    @Test
    void GivenId_WhenGetCustomer_ThenReturnMonoDto() {
        Long customerUdeAId = 1L;
        Customer customerUdeA = buildCustomerUdeA();
        when(customerRepository.findById(customerUdeAId))
                .thenReturn(Optional.of(customerUdeA));
        Mono<CustomerDto> monoCustomerUdeA = customerApplicationService.getCustomer(customerUdeAId);
        StepVerifier.create(monoCustomerUdeA)
                .consumeNextWith(customerDto ->
                        assertTrue(customerDtoEqualsCustomer(customerDto, customerUdeA)))
                .verifyComplete();
        verify(modelMapper)
                .map(customerUdeA, CustomerDto.class);
    }

    @Test
    void GivenWrongId_WhenGetCustomer_ThenThrowCustomerNotFound() {
        Long wrongId = 0L;
        when(customerRepository.findById(wrongId))
                .thenReturn(Optional.empty());
        assertThrows(CustomerNotFound.class,
                () -> customerApplicationService.getCustomer(wrongId));
    }

    private Customer buildCustomerUdeA() {
        return Customer.builder()
                .id(1L)
                .name("UdeA")
                .active(true)
                .build();
    }

    private boolean customerDtoEqualsCustomer(CustomerDto customerDto, Customer customer) {
        return Objects.equals(customerDto.getName(), customer.getName())
                && Objects.equals(customerDto.getActive(), customer.getActive());
    }
}