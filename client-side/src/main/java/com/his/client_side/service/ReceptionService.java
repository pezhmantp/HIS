package com.his.client_side.service;

import com.his.client_side.dto.PatientDto;
import com.his.client_side.model.doctor.Doctor;
import com.his.client_side.model.patient.Patient;
import com.his.client_side.model.reception.Reception;
import com.his.client_side.response.PatientsResponse;
import com.his.client_side.response.ReceptionPatientJoin;
import com.his.client_side.response.ReceptionsResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ReceptionService {
    PatientDto mapPatientDtoRest(PatientDto patientDto);
    List<Doctor> getDoctors();
    PatientsResponse getPatientsByPatientIds(ResponseEntity<ReceptionsResponse> responseEntity, HttpEntity<?> httpEntity);
    Boolean validateJWTExpirationTime(Authentication authentication);
    ReceptionPatientJoin joinPatientToReception(Patient patient, Reception reception);

}
