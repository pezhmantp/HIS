package com.patient_management.patient_core.responeObj;


import com.patient_management.patient_core.model.Patient;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
    private Patient patient;
    private String message;
}
