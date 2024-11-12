package com.pharmacy_management.pharmacy_query_ms.controller;

import com.pharmacy_management.pharmacy_core.model.MedicineRequest;
import com.pharmacy_management.pharmacy_core.query.FindAllMedicineRequestsQuery;
import com.pharmacy_management.pharmacy_core.query.FindMedicineRequestQuery;
import com.pharmacy_management.pharmacy_core.response.MedicineRequestsResponse;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicineRequestQuery")
public class MedicineRequestQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/getAllMedicineRequests")
    public ResponseEntity<?> getAllMedicineRequests(){
        FindAllMedicineRequestsQuery query = new FindAllMedicineRequestsQuery();
        MedicineRequestsResponse medicineRequestsResponse = queryGateway.query(query, ResponseTypes.instanceOf(MedicineRequestsResponse.class)).join();
        if(medicineRequestsResponse.getMedicineRequests().size()>0)
        return new ResponseEntity<>(medicineRequestsResponse, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getMedicineRequest/{medicineRequestId}")
    public ResponseEntity<?> getMedicineRequest(@PathVariable ("medicineRequestId") String medicineRequestId){
        FindMedicineRequestQuery query = new FindMedicineRequestQuery();
        query.setMedicineRequestId(medicineRequestId);
        MedicineRequest medicineRequest = queryGateway.query(query, ResponseTypes.instanceOf(MedicineRequest.class)).join();
        if(medicineRequest != null)
            return new ResponseEntity<>(medicineRequest, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
