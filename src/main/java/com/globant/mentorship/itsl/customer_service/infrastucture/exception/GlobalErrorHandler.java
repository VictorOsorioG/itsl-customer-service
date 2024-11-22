package com.globant.mentorship.itsl.customer_service.infrastucture.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@Order(-2)
@Component
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

    private final String LOG_PREFIX = "Global Error Handler >>>";

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.info("{} Exception {} caught. Caused by: {}",
                LOG_PREFIX, ex.getClass().getSimpleName(), ex.getMessage());
        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
        if (ex instanceof StandardException standardException) {
            log.info("{} Standard Exception with Error Code: {}",
                    LOG_PREFIX, standardException.getStandardError().getCode());
            return writeExchangeResponse(exchange, standardException.getHttpStatus(),
                    dataBufferFactory.wrap(standardException.getStandardError().toString().getBytes()));
        }
        if (ex instanceof WebExchangeBindException webExchangeBindException) {
            log.info("{} WebExchangeBind Exception", LOG_PREFIX);
            return writeExchangeResponse(exchange, HttpStatus.BAD_REQUEST,
                    dataBufferFactory.wrap(webExchangeBindException.getFieldErrors().stream()
                            .map(FieldError::getDefaultMessage)
                            .collect(Collectors.joining(",")).getBytes()));
        }
        return writeExchangeResponse(exchange, HttpStatus.INTERNAL_SERVER_ERROR,
                dataBufferFactory.wrap(StandardError.builder()
                        .code("E000")
                        .description("Server error")
                        .timestamp(LocalDateTime.now())
                        .build()
                        .toString()
                        .getBytes()
                ));
    }

    private Mono<Void> writeExchangeResponse(ServerWebExchange exchange, HttpStatus badRequest, DataBuffer errorMessage) {
        exchange.getResponse().setStatusCode(badRequest);
        return exchange.getResponse().writeWith(Mono.just(errorMessage));
    }
}