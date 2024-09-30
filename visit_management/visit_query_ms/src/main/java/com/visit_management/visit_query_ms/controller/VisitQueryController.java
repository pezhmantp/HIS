package com.visit_management.visit_query_ms.controller;

import com.visit_management.visit_core.query.FindVisitByReceptionIdQuery;
import com.visit_management.visit_core.response.VisitIdResponse;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visitQuery")
public class VisitQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @RequestMapping("/getVisitIdByReceptionId/{receptionId}")
    public ResponseEntity<VisitIdResponse> getVisitIdByReceptionId(@PathVariable ("receptionId") String receptionId)
    {
        FindVisitByReceptionIdQuery query =new FindVisitByReceptionIdQuery();
        query.setReceptionId(receptionId);
        String visitId = queryGateway.query(query, ResponseTypes.instanceOf(String.class)).join();
        System.out.println(" VisitQueryController ############# " + visitId);
        if (visitId != null)
        {
            return new ResponseEntity<>(new VisitIdResponse(visitId,"Visit Id found"), HttpStatus.FOUND);
        }
        else {
            return new ResponseEntity<>(new VisitIdResponse(null,"Visit Id not found"), HttpStatus.NOT_FOUND);
        }
    }
}
