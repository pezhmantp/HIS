package com.laboratory_management.laboratory_core.response;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteTestResponse {
    private String testId;
    private String visitId;
    private String status;
}
