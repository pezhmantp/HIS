package com.patient_management.patient_core.event;

import com.patient_management.patient_core.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientUpdatedEvent {
    private String id;
    private Patient patient;
}
