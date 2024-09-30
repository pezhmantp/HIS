package com.reception_management.reception_query_ms.controller;

import com.reception_management.reception_core.queries.FindAllOpenReceptionQuery;
import com.reception_management.reception_core.queries.FindOpenReceptionByPatientIdQuery;
import com.reception_management.reception_core.queries.FindReceptionByReceptionIdQuery;
import com.reception_management.reception_core.queries.FindReceptionsByDoctorIdQuery;
import com.reception_management.reception_core.responeObj.ReceptionResponse;
import com.reception_management.reception_core.responeObj.ReceptionsResponse;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/receptionQueries")
public class ReceptionQueryController {
    @Autowired
    private QueryGateway queryGateway;
    private static final Logger log= LoggerFactory.getLogger(ReceptionQueryController.class);

    @GetMapping("/byReceptionId/{receptionId}")
    public ResponseEntity<ReceptionResponse> findReceptionByReceptionId(@PathVariable(value = "receptionId") String receptionId)
    {
        try {
            FindReceptionByReceptionIdQuery query = new FindReceptionByReceptionIdQuery(receptionId);

            ReceptionResponse response = queryGateway.query(query, ResponseTypes.instanceOf(ReceptionResponse.class)).join();
            System.out.println(">>>>>>>>>>>>>>>>>> "+response.getReception());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get reception by receptionId request";
            log.error("Error at findReceptionByReceptionId(): " + e.getMessage());

            return new ResponseEntity<>(new ReceptionResponse(null,safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/byDoctorId/{doctorId}")
    public ResponseEntity<ReceptionsResponse> getReceptionsByDoctorId(@PathVariable(value = "doctorId") String doctorId)
    {
        try {
            FindReceptionsByDoctorIdQuery query = new FindReceptionsByDoctorIdQuery(doctorId);

            ReceptionsResponse response = queryGateway.query(query, ResponseTypes.instanceOf(ReceptionsResponse.class)).join();
            return new ResponseEntity<ReceptionsResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get receptions by doctorId request";
            log.error("Error at getReceptionsByDoctorId(): " + e.getMessage());

            return new ResponseEntity<>(new ReceptionsResponse(null,safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/openReception/byPatientId/{patientId}")
    public ResponseEntity<ReceptionResponse> getOpenReceptionsByPatientId(@PathVariable(value = "patientId") String patientId)
    {
        try {
            FindOpenReceptionByPatientIdQuery query = new FindOpenReceptionByPatientIdQuery(patientId);

            ReceptionResponse response = queryGateway.query(query, ResponseTypes.instanceOf(ReceptionResponse.class)).join();
            return new ResponseEntity<ReceptionResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get reception by patientId request";
            System.out.println(e.toString());
            log.error("Error at getOpenReceptionsByPatientId(): " + e.getMessage());

            return new ResponseEntity<>(new ReceptionResponse(null,safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/allOpenReceptions")
    public ResponseEntity<ReceptionsResponse> getAllReceptions()
    {

        try {
            FindAllOpenReceptionQuery query = new FindAllOpenReceptionQuery();

            ReceptionsResponse response = queryGateway.query(query, ResponseTypes.instanceOf(ReceptionsResponse.class)).join();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get reception by patientId request";
            log.error("Error at getAllReceptions(): " + e.getMessage());

            return new ResponseEntity<>(new ReceptionsResponse(null,safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
