package com.his.client_side.service;


import com.his.client_side.model.patient.Patient;
import com.his.client_side.model.reception.Reception;
import com.his.client_side.response.reception.ReceptionPatientJoin;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CommonService {
    String getJWT(Authentication authentication);
    List<ReceptionPatientJoin> joinPatientsToReceptions(List<Patient> patients, List<Reception> receptions);
    boolean checkRoles(String jwtToken, String roleName);
}
