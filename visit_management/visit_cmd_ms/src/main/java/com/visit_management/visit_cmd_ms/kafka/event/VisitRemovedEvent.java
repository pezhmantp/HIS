package com.visit_management.visit_cmd_ms.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.checkerframework.checker.units.qual.N;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitRemovedEvent {
    private String receptionId;
    private Boolean eventPublished;
    private Boolean status;
}
