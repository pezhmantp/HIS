package com.patient_management.patient_core.event;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRemovedEvent {
    private String id;
}
