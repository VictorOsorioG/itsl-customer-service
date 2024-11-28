package com.globant.mentorship.itsl.customer_service.application.service.impl;

import com.globant.mentorship.itsl.customer_service.application.dto.CustomerDto;
import com.globant.mentorship.itsl.customer_service.domain.model.Customer;
import com.globant.mentorship.itsl.customer_service.infrastucture.dto.CustomerRequest;
import com.globant.mentorship.itsl.customer_service.infrastucture.exception.standard_exception.CustomerNameUnique;
import com.globant.mentorship.itsl.customer_service.infrastucture.exception.standard_exception.CustomerNotFound;
import com.globant.mentorship.itsl.customer_service.infrastucture.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
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
        StepVerifier.create(customerApplicationService.getCustomer(wrongId))
                .expectError(CustomerNotFound.class)
                .verify();
    }

    @Test
    void WhenGetCustomerCatalogue_ThenReturnFluxDto() {
        Customer globantCustomer = Customer.builder()
                .name("Globant")
                .active(true)
                .build();
        Pageable pageable = PageRequest.of(1, 10);
        Page<Customer> customersPage = new PageImpl<>(List.of(globantCustomer, buildCustomerUdeA()));
        when(customerRepository.findAll(pageable))
                .thenReturn(customersPage);
        Flux<CustomerDto> customerDtoFlux = customerApplicationService.getCustomerCatalogue(pageable);
        StepVerifier.create(customerDtoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void GivenCustomerRequest_WhenCreateCustomer_ThenReturnMonoEmpty() {
        String udea = "UdeA";
        CustomerRequest customerUdeARequest = CustomerRequest.builder()
                .name(udea)
                .build();
        when(customerRepository.existsByName(udea))
                .thenReturn(false);
        customerApplicationService.createCustomer(customerUdeARequest);
        verify(customerRepository)
                .save(any(Customer.class));
    }

    @Test
    void GivenCustomerRequest_WhenCreateCustomer_ThenThrowCustomerNameUnique() {
        String udea = "UdeA";
        CustomerRequest customerUdeARequest = CustomerRequest.builder()
                .name("UdeA")
                .build();
        when(customerRepository.existsByName(udea))
                .thenReturn(true);
        StepVerifier.create(customerApplicationService.createCustomer(customerUdeARequest))
                .expectError(CustomerNameUnique.class)
                .verify();

    }

    @Test
    void GivenCustomerRequestAndId_WhenUpdateCustomer_TheReturnCustomerUpdated() {
        Long udeaId = 1L;
        CustomerRequest customerUdeARequest = CustomerRequest.builder()
                .name("UdeA")
                .active(false)
                .build();
        Customer customerUdeA = Customer.builder()
                .id(udeaId)
                .name("UdeA")
                .active(true)
                .build();
        when(customerRepository.findById(udeaId))
                .thenReturn(Optional.of(customerUdeA));
        StepVerifier.create(customerApplicationService.updateCustomer(udeaId, customerUdeARequest))
                .consumeNextWith(customerDto -> {
                    assert Objects.nonNull(customerDto);
                    assertFalse(customerDto.getActive());
                })
                .verifyComplete();
        verify(customerRepository)
                .save(any(Customer.class));
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