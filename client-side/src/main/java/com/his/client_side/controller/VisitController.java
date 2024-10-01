package com.his.client_side.controller;

import com.his.client_side.response.patient.PatientResponse;
import com.his.client_side.response.patient.PatientsResponse;
import com.his.client_side.response.reception.ReceptionPatientJoin;
import com.his.client_side.response.reception.ReceptionResponse;
import com.his.client_side.response.reception.ReceptionsResponse;
import com.his.client_side.service.CommonService;
import com.his.client_side.service.ReceptionService;
import com.his.client_side.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
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

            String receptionMsUri = "http://localhost:8085/api/receptionQueries/byDoctorId/"+principle.getUserInfo().getPreferredUsername();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);

            HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
            ResponseEntity<ReceptionsResponse> responseEntity = restTemplate.exchange(receptionMsUri, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ReceptionsResponse>() {
            });
            System.out.println("{{{{{{{{{{{{{{{{{{{{{{ : "+responseEntity.getBody().getReceptions().toString());
        if(responseEntity.getBody().getReceptions() != null)
        {

            PatientsResponse patientsResponses = receptionService.getPatientsByPatientIds(responseEntity,httpEntity);

            List<ReceptionPatientJoin> recpsRespnsForJoin = commonService.joinPatientsToReceptions(patientsResponses.getPatients(),responseEntity.getBody().getReceptions());
            System.out.println("okkkkkk " + recpsRespnsForJoin.toString());
            model.addAttribute("recpsRespnForJoin",recpsRespnsForJoin);
        }
        else {
            System.out.println("errrrrrrr");
            model.addAttribute("recpsRespnForJoin",null);
        }

//        model.addAttribute("recpsRespnForJoin",responseEntity.getBody().getReceptions());

        return "visitsList";
    }

    @GetMapping("/getVisit")
    public String getVisit(@RequestParam String receptionId){


        return "visit";
    }


    @GetMapping("/findReception/byReceptionId/{receptionId}")
    public String findReceptionReceptionId(@PathVariable("receptionId") String receptionId, Authentication authentication, Model model)
    {

//        String getReceptionUrl = "http://localhost:8091/visitCmd";
//        String jwtAccessToken = commonService.getJWT(authentication);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
//        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
//        HttpEntity<?> httpEntity = new HttpEntity<>(receptionId,httpHeaders);
//        restTemplate.exchange(getReceptionUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
//        });


        String getReceptionUrl = "http://localhost:8085/api/receptionQueries/byReceptionId/"+receptionId;
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

        System.out.println(">>>>>>>>>>>>>>>> " + receptionEntity.getBody().getReception());
        String getPatientUrl = "http://localhost:8082/api/patientQueries/byPatientId/"+
                receptionEntity.getBody().getReception().getPatientId();
        ResponseEntity<PatientResponse> patientEntity = restTemplate.exchange(getPatientUrl, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<PatientResponse>() {
        });
//        System.out.println("PatientResponse : " + patientEntity.getBody().getPatient());
        if (patientEntity.getBody().getPatient() != null)
        {
            ReceptionPatientJoin recpRespnsForJoin= receptionService.joinPatientToReception(patientEntity.getBody().getPatient(),
                    receptionEntity.getBody().getReception());
            model.addAttribute("recpsRespnForJoin",recpRespnsForJoin);
        }
        else {
            model.addAttribute("recpsRespnForJoin",null);
        }

        return "visit";
    }


    @PostMapping("/requestNewTest")
    public void requestNewTest(@RequestParam String visitId, @RequestParam String testType,
                               Authentication authentication)
    {
        String jwtAccessToken= commonService.getJWT(authentication);
        String laboratoryCmdUri = "http://localhost:8088/laboratoryCmd/test";
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
        System.out.println(">>>>>>>>>>>>> " + responseEntity.getBody().get("testId"));



    }
}
