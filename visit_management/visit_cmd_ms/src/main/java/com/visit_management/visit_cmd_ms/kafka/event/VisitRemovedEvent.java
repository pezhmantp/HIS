package com.visit_management.visit_cmd_ms.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitRemovedEvent {
    private String receptionId;
    private Boolean eventPublished;
    private Boolean status;
}
