package com.patient_management.patient_query_ms.controllers;


import com.patient_management.patient_core.queries.FindPatientByNationalIdQuery;
import com.patient_management.patient_core.queries.FindPatientByPatientIdQuery;
import com.patient_management.patient_core.queries.FindPatientsByPatientsIdsQuery;
import com.patient_management.patient_core.responeObj.PatientResponse;
import com.patient_management.patient_core.responeObj.PatientsResponse;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patientQueries")
public class PatientQueryController {

    private QueryGateway queryGateway;

    @Autowired
    public PatientQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @PreAuthorize("hasRole('receptionist')")
    @GetMapping("/byNationalId/{nationalId}")
    public ResponseEntity<PatientResponse> findPatientByNationalId(@PathVariable(value = "nationalId") String nationalId) {
        System.out.println("******/////////////////*****************//////////////");
        try {
            FindPatientByNationalIdQuery query = new FindPatientByNationalIdQuery(nationalId);
            System.out.println("From cntlr before " + nationalId);
            PatientResponse response = queryGateway.query(query, ResponseTypes.instanceOf(PatientResponse.class)).join();

//            if (response == null || response.getUsers() == null) {
//                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//            }
            if(response.getPatient() == null)
            {
                return new ResponseEntity<PatientResponse>(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<PatientResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get patient by personalId request";
            System.out.println(e.toString());

            return new ResponseEntity<PatientResponse>(new PatientResponse(null, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('receptionist','doctor')")
    @GetMapping("/byPatientId/{patientId}")
    public ResponseEntity<PatientResponse> findPatientByPatientId(@PathVariable(value = "patientId") String patientId) {
        System.out.println("******/////////////////*****************//////////////");
        try {
            FindPatientByPatientIdQuery query = new FindPatientByPatientIdQuery(patientId);
            System.out.println("From cntlr before " + patientId);
            PatientResponse response = queryGateway.query(query, ResponseTypes.instanceOf(PatientResponse.class)).join();

//            if (response == null || response.getUsers() == null) {
//                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//            }
            if(response.getPatient() == null)
            {
                return new ResponseEntity<PatientResponse>(response, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<PatientResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get patient by personalId request";
            System.out.println(e.toString());

            return new ResponseEntity<PatientResponse>(new PatientResponse(null, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('receptionist','doctor')")
    @GetMapping("/ListOfPatientsInfo")
    public ResponseEntity<?> getListOfPatientsInfo(@RequestParam ("patientsIds") List<String> patientsIds){
        FindPatientsByPatientsIdsQuery query=new FindPatientsByPatientsIdsQuery(patientsIds);

        PatientsResponse patientsResponse = queryGateway.query(query,ResponseTypes.instanceOf(PatientsResponse.class)).join();
        return new ResponseEntity<PatientsResponse>(patientsResponse,HttpStatus.OK);

    }
}
