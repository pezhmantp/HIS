package com.his.client_side.service;

import com.his.client_side.response.pharmacy.MedicinesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PharmacyServiceImpl implements PharmacyService{
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public MedicinesResponse getMedicineList(String jwtAccessToken) {
        String medicineQueryUri = "http://localhost:9095/medicineQuery/allMedicines";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<MedicinesResponse> responseEntity = restTemplate.exchange(medicineQueryUri, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
        });
        System.out.println("44444444444 "+responseEntity.getBody().getMedicines().toString());
        return responseEntity.getBody();
    }
}
