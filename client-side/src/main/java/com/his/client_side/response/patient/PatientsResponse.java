package com.his.client_side.response.patient;

import com.his.client_side.model.patient.Patient;
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

