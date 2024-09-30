package com.patient_management.patient_core.responeObj;

import com.patient_management.patient_core.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientsResponse {
    private List<Patient> patients;
    private String message;
}

