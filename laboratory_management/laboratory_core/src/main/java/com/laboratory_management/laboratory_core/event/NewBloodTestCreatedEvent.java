package com.laboratory_management.laboratory_core.event;

import com.laboratory_management.laboratory_core.model.BloodTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBloodTestCreatedEvent {
    private String id;
    private BloodTest bloodTest;
}
