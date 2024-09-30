package com.reception_management.reception_cmd_ms.kafka.event;

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
