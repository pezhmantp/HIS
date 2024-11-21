package com.api_gateway.api_gateway_service;


import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

@Configuration
public class GatewayConfig {

    private static final String HEADER_FOR_KEY_RESOLVER = "Authorization";

    @Bean(name = "authHeaderResolver")
    KeyResolver userKeyResolver() {
        return exchange -> Mono.just(Objects.requireNonNull(exchange
                .getRequest().getHeaders().getFirst(HEADER_FOR_KEY_RESOLVER)));
    }


}
