package com.visit_management.visit_cmd_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitStatusDto {
    private String receptionId;
    private String visitStatus;
}
