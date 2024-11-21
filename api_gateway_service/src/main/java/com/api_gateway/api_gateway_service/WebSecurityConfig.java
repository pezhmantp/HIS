package com.api_gateway.api_gateway_service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain webFluxSecurityConfig(ServerHttpSecurity httpSecurity) {
        httpSecurity.authorizeExchange(a -> a.anyExchange().permitAll());

        httpSecurity.csrf(c -> c.disable());
        return httpSecurity.build();
    }
}
