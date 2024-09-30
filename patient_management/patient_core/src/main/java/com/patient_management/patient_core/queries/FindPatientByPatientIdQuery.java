package com.patient_management.patient_core.queries;
//
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindPatientByPatientIdQuery {
    private String patientId;
}
