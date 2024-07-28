package com.reception_management.reception_query_ms.controller;

import com.reception_management.reception_core.queries.FindReceptionByReceptionIdQuery;
import com.reception_management.reception_core.queries.FindReceptionsByDoctorIdQuery;
import com.reception_management.reception_core.responeObj.ReceptionResponse;
import com.reception_management.reception_core.responeObj.ReceptionResponseFromQuery;
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

//            if (response == null || response.getUsers() == null) {
//                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//            }

            return new ResponseEntity<ReceptionResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get reception by receptionId request";
            System.out.println(e.toString());

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
            System.out.println(e.toString());

            return new ResponseEntity<>(new ReceptionsResponse(null,safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
