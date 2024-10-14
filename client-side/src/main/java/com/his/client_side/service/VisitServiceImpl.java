package com.his.client_side.service;

import com.his.client_side.response.visit.VisitIdResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VisitServiceImpl implements VisitService{

    @Autowired
    private CommonService commonService;
    @Autowired
    private RestTemplate restTemplate;

    private static final Logger log= LoggerFactory.getLogger(VisitServiceImpl.class);

    @Override
    public String createNewVisit(String receptionId,String jwtAccessToken) {
        String getVisitUrl = "http://localhost:8091/visitCmd";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(receptionId,httpHeaders);
        ResponseEntity<String> receptionEntity = restTemplate.exchange(getVisitUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
        });
        return receptionEntity.getBody();

    }

    @Override
    public String getVisitIdByReceptionId(String receptionId,String jwtAccessToken) {
        String getVisitUrl = "http://localhost:8092/visitQuery/getVisitIdByReceptionId/"+receptionId;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        try {
            ResponseEntity<VisitIdResponse> receptionEntity = restTemplate.exchange(getVisitUrl, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
            });

            if (receptionEntity.getBody().getVisitId() != null) {
                return receptionEntity.getBody().getVisitId();
            }
            else {
                log.error("No visit has been created for the receptionId: " + receptionId);
                return null;
            }
        }
        catch (Exception e)
        {
            log.error("No visit has been created for the receptionId: " + receptionId);
            return null;
        }



    }
}
