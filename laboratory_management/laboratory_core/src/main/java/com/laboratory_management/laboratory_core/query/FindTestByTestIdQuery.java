package com.laboratory_management.laboratory_core.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindTestByTestIdQuery {
    private String testId;
    private String testType;
}
