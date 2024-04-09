package com.patient_management.patient_cmd_ms.service;

import com.patient_management.patient_core.model.Patient;
import com.patient_management.patient_core.responeObj.PatientResponse;
import com.patient_management.patient_cmd_ms.dto.PatientDto;

public interface PatientService {
    String generatePatientId();
    Patient mapPatientDtoToPatient(PatientDto patientDto);
    PatientResponse fetchPatient(String nationalId);
    Patient fetchPatient2(String nationalId);
}
