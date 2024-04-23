package com.reception_management.reception_query_ms.controller;

import com.reception_management.reception_core.queries.FindReceptionByReceptionIdQuery;
import com.reception_management.reception_core.responeObj.ReceptionResponseFromQuery;
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
    public ResponseEntity<ReceptionResponseFromQuery> findReceptionByReceptionId(@PathVariable(value = "receptionId") String receptionId)
    {
        try {
            FindReceptionByReceptionIdQuery query = new FindReceptionByReceptionIdQuery(receptionId);

            ReceptionResponseFromQuery response = queryGateway.query(query, ResponseTypes.instanceOf(ReceptionResponseFromQuery.class)).join();

//            if (response == null || response.getUsers() == null) {
//                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//            }

            return new ResponseEntity<ReceptionResponseFromQuery>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get patient by personalId request";
            System.out.println(e.toString());

            return new ResponseEntity<>(new ReceptionResponseFromQuery(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
