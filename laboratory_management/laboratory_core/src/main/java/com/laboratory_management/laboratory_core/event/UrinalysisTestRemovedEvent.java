package com.laboratory_management.laboratory_core.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrinalysisTestRemovedEvent {
    private String id;
}
