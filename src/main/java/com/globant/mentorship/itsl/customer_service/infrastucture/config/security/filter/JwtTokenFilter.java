package com.globant.mentorship.itsl.customer_service.infrastucture.config.security.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class JwtTokenFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        final String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ") && authorizationHeader.contains("2")) {
            final String token = authorizationHeader.replace("Bearer ", "");
            UserDetails userDetails = User.withUsername("user")
                    .password(passwordEncoder().encode("password"))
                    .roles("USER")
                    .build();
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authenticationToken));

            //TODO -> Build authentication service and token service to validate token and build authorities
        }
        return chain.filter(exchange);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

