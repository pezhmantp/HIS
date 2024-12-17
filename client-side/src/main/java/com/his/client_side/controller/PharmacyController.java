package com.his.client_side.controller;

import com.his.client_side.model.pharmacy.Medicine;
import com.his.client_side.model.pharmacy.MedicineRequest;
import com.his.client_side.response.pharmacy.MedicineRequestsResponse;
import com.his.client_side.response.pharmacy.MedicinesResponse;
import com.his.client_side.response.reception.ReceptionResponse;
import com.his.client_side.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/pharmacy")
public class PharmacyController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CommonService commonService;

    @GetMapping("/pharmacyPage")
    public String getPharmacyPage(Authentication authentication, Model model){
        String jwtAccessToken= commonService.getJWT(authentication);
        boolean isAuthorized= commonService.checkRoles(jwtAccessToken,"pharmacist");
        if(!isAuthorized){
            return "unAuthorized";
        }

        return "pharmacy";
    }

    @PostMapping("/addNewMedicine")
    public ResponseEntity<?> addNewMedicine(@RequestParam String medicineName, Authentication authentication){
        String jwtAccessToken = commonService.getJWT(authentication);

        String medicineQueryUri = "http://localhost:9096/medicineCmd";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);

        HttpEntity<?> httpEntity = new HttpEntity<>(medicineName,httpHeaders);

        ResponseEntity<String> medicineId = restTemplate.exchange(medicineQueryUri, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
        });
        return new ResponseEntity<>(medicineName,HttpStatus.OK);
    }
    @GetMapping("/getMedicineRequest/{medicineRequestId}")
    public ResponseEntity<MedicineRequest> getMedicineRequest(@PathVariable ("medicineRequestId") String medicineRequestId,Authentication authentication){
        String jwtAccessToken = commonService.getJWT(authentication);


        String medicineQueryUri = "http://localhost:9096/medicineRequestQuery/getMedicineRequest/"+medicineRequestId;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);

        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<MedicineRequest> responseEntity = restTemplate.exchange(medicineQueryUri, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
        });
        if(responseEntity.getStatusCode() == HttpStatus.OK)
        {
            return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

    }
//
    @GetMapping("/getAllMedicineRequests")
    public ResponseEntity<List<MedicineRequest>> getAllMedicineRequests(Authentication authentication){
        String jwtAccessToken = commonService.getJWT(authentication);


        String medicineQueryUri = "http://localhost:9096/medicineRequestQuery/getAllMedicineRequests";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);

        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);


        ResponseEntity<MedicineRequestsResponse> responseEntity = restTemplate.exchange(medicineQueryUri, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
        });
        if(responseEntity.getBody().getMessage() == null)
        {
            if(responseEntity.getBody().getMedicineRequests().size() > 0)
            {
                return new ResponseEntity<>(responseEntity.getBody().getMedicineRequests(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
//        if(responseEntity.getBody().getMessage().equals("fallback"))
//        {
//            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//        }




    }
}
