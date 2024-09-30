package com.visit_management.visit_core.response;

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
