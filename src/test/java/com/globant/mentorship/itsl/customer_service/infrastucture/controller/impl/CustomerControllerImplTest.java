package com.globant.mentorship.itsl.customer_service.infrastucture.controller.impl;

import com.globant.mentorship.itsl.customer_service.application.dto.CustomerDto;
import com.globant.mentorship.itsl.customer_service.application.service.CustomerApplicationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

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

    @Test
    void WhenGetCustomerCatalogue_ThenReturnFluxCustomer() {
        CustomerDto globantCustomer = CustomerDto.builder()
                .name("Globant")
                .active(true)
                .build();
        int pageNumber = 1;
        int pageSize = 10;
        Mockito.when(customerApplicationService.getCustomerCatalogue(PageRequest.of(pageNumber, pageSize)))
                .thenReturn(Flux.fromIterable(List.of(globantCustomer, buildCustomerUdeADto())));
        webTestClient.get()
                .uri(UriComponentsBuilder.fromUriString(BASE_URL + "/catalogue")
                        .queryParam("pageNumber", pageNumber)
                        .queryParam("pageSize", pageSize)
                        .toUriString()
                )
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(CustomerDto.class)
                .consumeWith(listEntityExchangeResult -> {
                   assert (Objects.requireNonNull(listEntityExchangeResult.getResponseBody()).size() == 2);
                });
    }

    private CustomerDto buildCustomerUdeADto() {
        return CustomerDto.builder()
                .name("UdeA")
                .active(true)
                .build();
    }

}