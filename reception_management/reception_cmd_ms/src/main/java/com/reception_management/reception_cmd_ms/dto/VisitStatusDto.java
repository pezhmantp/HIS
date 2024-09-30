package com.reception_management.reception_cmd_ms.dto;

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
