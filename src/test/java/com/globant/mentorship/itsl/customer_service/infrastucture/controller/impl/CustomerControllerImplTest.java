package com.globant.mentorship.itsl.customer_service.infrastucture.controller.impl;

import com.globant.mentorship.itsl.customer_service.application.dto.CustomerDto;
import com.globant.mentorship.itsl.customer_service.application.service.CustomerApplicationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureWebTestClient
@WebFluxTest(CustomerControllerImpl.class)
class CustomerControllerImplTest {

    private final String BASE_URL = "/api/v1/customer";

    @MockBean
    private CustomerApplicationService customerApplicationService;
    @Autowired
    WebTestClient webTestClient;

    @Test
    void GivenId_WhenGetCustomerById_ThenReturnMonoCustomer() {
        Long customerUdeAId = 1L;
        CustomerDto customerUdeADto = buildCustomerUdeADto();
        Mockito.when(customerApplicationService.getCustomer(customerUdeAId))
                .thenReturn(Mono.just(customerUdeADto));
        webTestClient.get()
                .uri(BASE_URL + "/" + customerUdeAId)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(CustomerDto.class)
                .consumeWith(customerDtoEntityExchangeResult -> {
                    CustomerDto customerDtoResponse = customerDtoEntityExchangeResult.getResponseBody();
                    assertEquals(customerUdeADto, customerDtoResponse);
                });
        Mockito.verify(customerApplicationService)
                .getCustomer(customerUdeAId);
    }

    private CustomerDto buildCustomerUdeADto() {
        return CustomerDto.builder()
                .name("UdeA")
                .active(true)
                .build();
    }

}