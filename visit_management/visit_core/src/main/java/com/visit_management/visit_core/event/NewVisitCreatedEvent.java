package com.visit_management.visit_core.event;

import com.visit_management.visit_core.model.Visit;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewVisitCreatedEvent {
    private String id;
    private Visit visit;
}
