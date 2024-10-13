package com.laboratory_management.laboratory_query_ms.controller;

import com.laboratory_management.laboratory_core.query.FindAllOpenTestsQuery;
import com.laboratory_management.laboratory_core.query.FindTestByTestIdQuery;
import com.laboratory_management.laboratory_core.query.FindTestsByVisitIdQuery;
import com.laboratory_management.laboratory_core.response.CompleteTestResponse;
import com.laboratory_management.laboratory_core.response.TestResponse;
import com.laboratory_management.laboratory_core.response.TestResponses;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/laboratoryQuery")
public class LabQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/getTestsByVisitId/{visitId}")
    public ResponseEntity<?> getTestsByVisitId(@PathVariable ("visitId") String visitId){
        FindTestsByVisitIdQuery query=new FindTestsByVisitIdQuery();
        query.setVisitId(visitId);
        TestResponses testResponses = queryGateway.query(query, ResponseTypes.instanceOf(TestResponses.class)).join();
        return new ResponseEntity<>(testResponses, HttpStatus.OK);
    }
    @GetMapping("/getOpenTests")
    public ResponseEntity<?> getOpenTests(){
        FindAllOpenTestsQuery query=new FindAllOpenTestsQuery();

        TestResponses testResponses = queryGateway.query(query, ResponseTypes.instanceOf(TestResponses.class)).join();
        return new ResponseEntity<>(testResponses, HttpStatus.OK);
    }
    @GetMapping("/getTestResultByTestId")
    public ResponseEntity<CompleteTestResponse> getTestResultByTestId(@RequestParam String testId, @RequestParam String testType){

        FindTestByTestIdQuery query=new FindTestByTestIdQuery();
        query.setTestId(testId);
        query.setTestType(testType);
        CompleteTestResponse completeTestResponse = queryGateway.query(query, ResponseTypes.instanceOf(CompleteTestResponse.class)).join();
        System.out.println("########## : " + completeTestResponse.toString());
        return new ResponseEntity<>(completeTestResponse, HttpStatus.OK);
    }
}
