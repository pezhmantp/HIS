package com.visit_management.visit_core.event;

import com.visit_management.visit_core.model.Visit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitUpdatedEvent {
    private String id;
    private Visit visit;
}
