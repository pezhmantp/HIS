package com.his.client_side.response.visit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitIdResponse {
    private String visitId;
    private String message;
}
