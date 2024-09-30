package com.visit_management.visit_core.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindVisitByReceptionIdQuery {
    private String receptionId;
}
