package com.pharmacy_management.pharmacy_query_ms.controller;

import com.pharmacy_management.pharmacy_core.model.Medicine;
import com.pharmacy_management.pharmacy_core.query.FindAllMedicinesQuery;
import com.pharmacy_management.pharmacy_core.query.FindMedicineByNameQuery;
import com.pharmacy_management.pharmacy_core.response.MedicinesResponse;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medicineQuery")
public class MedicineQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/allMedicines")
    public ResponseEntity<MedicinesResponse> getAllMedicines(){
        FindAllMedicinesQuery query=new FindAllMedicinesQuery();
        MedicinesResponse medicinesResponse=queryGateway.query(query, ResponseTypes.instanceOf(MedicinesResponse.class)).join();
        return new ResponseEntity<>(medicinesResponse, HttpStatus.OK);
    }
//    @GetMapping("/getMedicineByName/{name}")
//    public ResponseEntity<?> getMedicineByName(@PathVariable ("name") String name){
//        FindMedicineByNameQuery query=new FindMedicineByNameQuery();
//        String medicineName=queryGateway.query(query, ResponseTypes.instanceOf(String.class)).join();
//        return new ResponseEntity<>(medicineName, HttpStatus.OK);
//    }
}
