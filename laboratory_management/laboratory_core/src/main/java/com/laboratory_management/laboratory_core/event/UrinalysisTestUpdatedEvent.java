package com.laboratory_management.laboratory_core.event;

import com.laboratory_management.laboratory_core.model.UrinalysisTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrinalysisTestUpdatedEvent {
    private String id;
    private UrinalysisTest urinalysisTest;
}
