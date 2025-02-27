package com.his.client_side.controller;

import com.his.client_side.dto.MedicineRequestDto;
import com.his.client_side.dto.PrescriptionDto;
import com.his.client_side.model.pharmacy.MedicineRequest;
import com.his.client_side.response.laboratory.*;
import com.his.client_side.response.patient.PatientResponse;
import com.his.client_side.response.patient.PatientsResponse;
import com.his.client_side.response.pharmacy.MedicinesResponse;
import com.his.client_side.response.reception.ReceptionPatientJoin;
import com.his.client_side.response.reception.ReceptionResponse;
import com.his.client_side.response.reception.ReceptionsResponse;
import com.his.client_side.response.reception.ResponseMsgWithBoolean;
import com.his.client_side.service.CommonService;
import com.his.client_side.service.PharmacyService;
import com.his.client_side.service.ReceptionService;
import com.his.client_side.service.VisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CommonService commonService;
    @Autowired
    private VisitService visitService;
    private static final Logger LOG = LoggerFactory.getLogger(VisitController.class);
    @Autowired
    private PharmacyService pharmacyService;
    @Autowired
    private ReceptionService receptionService;

    @GetMapping("/receptionsList/byDoctorId")
    public String getVisitsList(Model model, Authentication authentication,
                                @AuthenticationPrincipal OidcUser principle){
            String jwtAccessToken= commonService.getJWT(authentication);
            boolean isAuthorized= commonService.checkRoles(jwtAccessToken,"doctor");
            if(!isAuthorized){
                return "unAuthorized";
            }

            String receptionMsUri = "http://localhost:9096/api/receptionQueries/byDoctorId/"+principle.getUserInfo().getPreferredUsername();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);

            HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
            ResponseEntity<ReceptionsResponse> responseEntity = restTemplate.exchange(receptionMsUri, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ReceptionsResponse>() {
            });
            if(!responseEntity.getBody().getMessage().equals("fallback"))
            {
                if(responseEntity.getBody().getReceptions() != null)
                {
                    PatientsResponse patientsResponses = receptionService.getPatientsByPatientIds(responseEntity,httpEntity);

                    if(patientsResponses.getPatients().size() > 0)
                    {
                        List<ReceptionPatientJoin> recpsRespnsForJoin = commonService.joinPatientsToReceptions(patientsResponses.getPatients(),responseEntity.getBody().getReceptions());
                        model.addAttribute("recpsRespnForJoin",recpsRespnsForJoin);
                    }
                    else {
                        model.addAttribute("recpsRespnForJoin",null);
                    }

                }
                else {
                    model.addAttribute("recpsRespnForJoin",null);
                }
            }
            else {
                LOG.error("Message from fallback method of reception_query_ms");
            }

        return "visitsList";
    }

    @GetMapping("/getVisit")
    public String getVisit(@RequestParam String receptionId){


        return "visit";
    }


    @GetMapping("/findReception/byReceptionId/{receptionId}")
    public String findReceptionReceptionId(@PathVariable("receptionId") String receptionId, Authentication authentication, Model model)
    {
        String getReceptionUrl = "http://localhost:9096/api/receptionQueries/byReceptionId/"+receptionId;
        String jwtAccessToken = commonService.getJWT(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<ReceptionResponse> receptionEntity = restTemplate.exchange(getReceptionUrl, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ReceptionResponse>() {
        });


        String visitId = visitService.getVisitIdByReceptionId(receptionId,jwtAccessToken);

        if(visitId != null)
        {
            model.addAttribute("visitId",visitId);
        }
        else {
            visitId = visitService.createNewVisit(receptionId,jwtAccessToken);
            model.addAttribute("visitId",visitId);
        }

        String getPatientUrl = "http://localhost:9096/api/patientQueries/byPatientId/"+
                receptionEntity.getBody().getReception().getPatientId();
        ResponseEntity<PatientResponse> patientEntity = restTemplate.exchange(getPatientUrl, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<PatientResponse>() {
        });
        if (patientEntity.getBody().getPatient() != null)
        {
            ReceptionPatientJoin recpRespnsForJoin= receptionService.joinPatientToReception(patientEntity.getBody().getPatient(),
                    receptionEntity.getBody().getReception());
            model.addAttribute("recpsRespnForJoin",recpRespnsForJoin);
            MedicinesResponse medicinesResponse = pharmacyService.getMedicineList(jwtAccessToken);
            model.addAttribute("medicinesList",medicinesResponse.getMedicines());
        }
        else {
            model.addAttribute("recpsRespnForJoin",null);
        }

        return "visit";
    }

    @GetMapping("/changeVisitStatus/{receptionId}")
    public ResponseEntity<?> changeVisitStatus(@PathVariable ("receptionId") String receptionId, Authentication authentication){
        String jwtAccessToken = commonService.getJWT(authentication);
        String changeVisitStatusUri = "http://localhost:9096/receptionCmd/changeVisitStatus";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(receptionId,httpHeaders);

        ResponseEntity<ResponseMsgWithBoolean> receptionResponseEntity = restTemplate.exchange(changeVisitStatusUri, HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<ResponseMsgWithBoolean>() {
        });
        System.out.println("=============== : " + receptionResponseEntity.getBody().toString());
        if(receptionResponseEntity.getBody().getResult())
        {

            return new ResponseEntity<>(true,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(false,HttpStatus.OK);
        }
    }
    @PostMapping("/requestNewTest")
    public ResponseEntity<?> requestNewTest(@RequestParam String visitId, @RequestParam String testType,
                               Authentication authentication)
    {
        String jwtAccessToken= commonService.getJWT(authentication);
        String laboratoryCmdUri = "http://localhost:9096/laboratoryCmd/test";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);

        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        URI uri = UriComponentsBuilder
                .fromUri(URI.create(laboratoryCmdUri))
                .queryParam("visitId", visitId)
                .queryParam("testType",testType)
                .build()
                .toUri();
        ResponseEntity<Map<String,String>> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<>() {});
//        System.out.println("? testId" + responseEntity.getBody().get("testId"));
//        System.out.println("? visitId" + responseEntity.getBody().get("visitId"));
        return new ResponseEntity<>("",HttpStatus.OK);
    }
    @GetMapping("/getTestsByVisitId/{visitId}")
    public ResponseEntity<?> getTestsByVisitId(@PathVariable ("visitId") String visitId, Authentication authentication)
    {
        String jwtAccessToken= commonService.getJWT(authentication);
        String laboratoryQueryUri = "http://localhost:9096/laboratoryQuery/getTestsByVisitId/"+visitId;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);

        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<TestResponses> testResponses = restTemplate.exchange(laboratoryQueryUri, HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<>() {});
//        System.out.println(">>>>>>>>>>>>> testId" + testResponses.getBody().getTestResponses());
        return new ResponseEntity<>(testResponses.getBody().getTestResponses(),HttpStatus.OK);
    }
    @GetMapping("/removeTest")
    public ResponseEntity<?> removeTest(@RequestParam String testId,@RequestParam String testType, Authentication authentication)
    {
//        System.out.println("testId " + testId +"  testType " + testType);
        String jwtAccessToken= commonService.getJWT(authentication);
        String laboratoryCmdUri = "http://localhost:9096/laboratoryCmd/test";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);

        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        URI uri = UriComponentsBuilder
                .fromUri(URI.create(laboratoryCmdUri))
                .queryParam("testId", testId)
                .queryParam("testType",testType)
                .build()
                .toUri();
        ResponseEntity<Map<String,String>> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, httpEntity,
                new ParameterizedTypeReference<>() {});
//        System.out.println(">>>>>>>>>>>>> testId" + responseEntity.getBody().get("testId"));
        return new ResponseEntity<>(responseEntity.getBody().get("responseEntity"),HttpStatus.OK);
//        return new ResponseEntity<>("klj",HttpStatus.OK);
    }
    @GetMapping("/getTestResult")
    public ResponseEntity<?> getTestResult(@RequestParam String testId,@RequestParam String testType, Authentication authentication)
    {
//        System.out.println("testId " + testId +"  testType " + testType);
        String jwtAccessToken= commonService.getJWT(authentication);
        String laboratoryCmdUri = "http://localhost:9096/laboratoryQuery/getTestResultByTestId";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);

        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        URI uri = UriComponentsBuilder
                .fromUri(URI.create(laboratoryCmdUri))
                .queryParam("testId", testId)
                .queryParam("testType",testType)
                .build()
                .toUri();
        ResponseEntity<CompleteTestResponse> completeTestResponse = restTemplate.exchange(uri, HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<>() {});
        completeTestResponse.getBody().setClassName(completeTestResponse.getBody().getClass().getSimpleName());
        return new ResponseEntity<>(completeTestResponse.getBody(),HttpStatus.OK);
    }

    @PostMapping("/sendPrescription/{visitId}")
    public ResponseEntity<?> sendPrescription(@RequestBody List<PrescriptionDto> prescriptionDtos,@PathVariable ("visitId") String visitId, Authentication authentication){

        MedicineRequestDto medicineRequestDto=new MedicineRequestDto();
        medicineRequestDto.setVisitId(visitId);
        medicineRequestDto.setPrescriptionsDto(prescriptionDtos);
        String jwtAccessToken= commonService.getJWT(authentication);
        String pharmacyCmdUri = "http://localhost:9096/medicineRequestCmd";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);

        HttpEntity<?> httpEntity = new HttpEntity<>(medicineRequestDto,httpHeaders);
        ResponseEntity<String> result = restTemplate.exchange(pharmacyCmdUri, HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<>() {});
        return new ResponseEntity<>(result.getBody(),HttpStatus.OK);
    }
}
