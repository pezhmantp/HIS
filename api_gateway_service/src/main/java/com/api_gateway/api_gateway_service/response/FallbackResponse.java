package com.api_gateway.api_gateway_service.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FallbackResponse {
    private Object object;
    private String message;
}
