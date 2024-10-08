package com.laboratory_management.laboratory_core.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResponse {
    private String testId;
    private String visitId;
    private String testType;
    private String status;
}
