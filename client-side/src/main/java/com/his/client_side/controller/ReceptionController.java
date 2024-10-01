package com.his.client_side.controller;

import com.his.client_side.dto.ReceptionDto;
import com.his.client_side.response.patient.PatientResponse;
import com.his.client_side.response.patient.PatientsResponse;
import com.his.client_side.response.reception.ReceptionPatientJoin;
import com.his.client_side.response.reception.ReceptionResponse;
import com.his.client_side.response.reception.ReceptionsResponse;
import com.his.client_side.response.reception.ResponseMsgWithBoolean;
import com.his.client_side.service.CommonService;
import com.his.client_side.service.ReceptionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reception")
public class ReceptionController {

    @Autowired
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
    @Autowired
    private ReceptionService receptionService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/receptionFrom")
    public String getReceptionView(Model model, Authentication authentication,
                                   @AuthenticationPrincipal OidcUser principle,
                                   String patientIdAttr, RedirectAttributes attributes){
        String jwtAccessToken= commonService.getJWT(authentication);
        boolean isAuthorized= commonService.checkRoles(jwtAccessToken,"receptionist");
        if(!isAuthorized){
            return "unAuthorized";
        }
        if(patientIdAttr ==null)
        {
            return "redirect:/patient/patientFrom";
        }
        OidcIdToken openIdToken = principle.getIdToken();
        String idTokenVal= openIdToken.getTokenValue();
        model.addAttribute("doctorsList",receptionService.getDoctors());
        ReceptionDto receptionDto = new ReceptionDto();
        model.addAttribute("patientId", patientIdAttr);
        model.addAttribute("receptionDto", receptionDto);
        model.addAttribute("username", openIdToken.getPreferredUsername());

        return "reception";
    }
    @PostMapping("/saveReception")
    public String saveReception(@Valid @ModelAttribute("receptionDto") ReceptionDto receptionDto, BindingResult result,
                                Authentication authentication,Model model){

        //   PatientDto patientDto=new PatientDto();
        model.addAttribute("doctorsList",receptionService.getDoctors());
        model.addAttribute("patientId",receptionDto.getPatientId());


        if(!result.hasErrors()) {
            System.out.println("REEEEEEEEEEEception" + receptionDto.toString());
            String jwtAccessToken = commonService.getJWT(authentication);
            String receptionMsUri = "http://localhost:8084/receptionCmd";
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);

            HttpEntity<?> httpEntity = new HttpEntity<>(receptionDto, httpHeaders);
            ResponseEntity<ReceptionResponse> responseEntity = restTemplate.exchange(receptionMsUri, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ReceptionResponse>() {
            });
            model.addAttribute("receptionId",responseEntity.getBody().getReception().getReceptionId());

            System.out.println("NOttttttttttt errrrrrrrrrrrrrrrrrrrrrr: " + responseEntity.getBody().getReception().getReceptionId());
        }
        return "reception";
    }

    @GetMapping("/changeReceptionStatusToCompleted/{receptionId}")
    public ResponseEntity<?> changeReceptionStatusToCompleted(@PathVariable String receptionId, Authentication authentication){


        String jwtAccessToken = commonService.getJWT(authentication);


        String chngReceptionStatUri = "http://localhost:8084/receptionCmd/changeReceptionStatusToCompleted";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);

        HttpEntity<?> httpEntity = new HttpEntity<>(receptionId,httpHeaders);

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        ResponseEntity<ReceptionResponse> receptionResponseEntity = restTemplate.exchange(chngReceptionStatUri, HttpMethod.PATCH, httpEntity, new ParameterizedTypeReference<ReceptionResponse>() {
        });

        String getReceptionUri = "http://localhost:8085/api/receptionQueries/byReceptionId/"+receptionId;
        ResponseEntity<ReceptionResponse> receptionResponse = restTemplate.exchange(getReceptionUri, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ReceptionResponse>() {
        });


        return new ResponseEntity<>(receptionResponse.getBody().getReception(),HttpStatus.OK);
    }


    @GetMapping("/receptionsList")
    public String getReceptionsList(Model model, Authentication authentication,
                                    HttpServletRequest request){

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            String deleteMsg = (String) inputFlashMap.get("deleteMsg");
            Boolean deleteResult = (Boolean) inputFlashMap.get("deleteResult");
            model.addAttribute("deleteMsg",deleteMsg);
            model.addAttribute("deleteResult",deleteResult);
        }

        String jwtAccessToken = commonService.getJWT(authentication);

        String allReceptionQueryUri = "http://localhost:8085/api/receptionQueries/allOpenReceptions";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<ReceptionsResponse> responseEntity = restTemplate.exchange(allReceptionQueryUri, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ReceptionsResponse>() {
        });

        if(responseEntity.getBody().getReceptions() != null)
        {
            PatientsResponse patientsResponses = receptionService.getPatientsByPatientIds(responseEntity,httpEntity);
            List<ReceptionPatientJoin> recpsRespnsForJoin = commonService.joinPatientsToReceptions(patientsResponses.getPatients(),responseEntity.getBody().getReceptions());
            model.addAttribute("recpsRespnForJoin",recpsRespnsForJoin);
        }
        else {
            model.addAttribute("recpsRespnForJoin",null);
        }

        return "receptionsList";
    }

    @GetMapping("/getVisitStatus/{receptionId}")
    public ResponseEntity<?> isVisited(@PathVariable ("receptionId") String receptionId, Authentication authentication){
        String jwtAccessToken = commonService.getJWT(authentication);

        String allReceptionQueryUri = "http://localhost:8085/api/receptionQueries/byReceptionId/"+receptionId;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<ReceptionResponse> responseEntity = restTemplate.exchange(allReceptionQueryUri, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ReceptionResponse>() {
        });

        if(responseEntity.getBody().getReception().getVisitStatus().equals("visited"))
        {
            System.out.println("resssss: " + responseEntity.getBody().getReception().getVisitStatus());
            return new ResponseEntity<>(true,HttpStatus.OK);
        }
        else {
            System.out.println("resssss: " + responseEntity.getBody().getReception().getVisitStatus());
            return new ResponseEntity<>(false,HttpStatus.OK);
        }


    }

    @GetMapping("/delete/{receptionId}")
    public String deleteReception(@PathVariable ("receptionId") String receptionId,
                                  Authentication authentication, Model model,
                                  RedirectAttributes attributes){
        String deleteReceptionUri = "http://localhost:8084/receptionCmd/"+receptionId;
        String jwtAccessToken = commonService.getJWT(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<ResponseMsgWithBoolean> responseEntity = restTemplate.exchange(deleteReceptionUri, HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<ResponseMsgWithBoolean>() {
        });

        if(responseEntity.getBody().getResult())
        {
            attributes.addFlashAttribute("deleteMsg","Reception deleted successfully");
            attributes.addFlashAttribute("deleteResult",true);
        }
        else {
            attributes.addFlashAttribute("deleteMsg","Error on deleting the reception");
            attributes.addFlashAttribute("deleteResult",false);
        }

        return "redirect:/reception/receptionsList";
    }

    @GetMapping("/findReception/byNationalId")
    public String findReceptionByNationalId(@RequestParam String nationalId, Authentication authentication, Model model)
    {
        String getPatientUrl = "http://localhost:8082/api/patientQueries/byNationalId/"+nationalId;
        String jwtAccessToken = commonService.getJWT(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<PatientResponse> patientEntity = restTemplate.exchange(getPatientUrl, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<PatientResponse>() {
        });
        String getReceptionUrl = "http://localhost:8085/api/receptionQueries/openReception/byPatientId/"+
                patientEntity.getBody().getPatient().getPatientId();
        ResponseEntity<ReceptionResponse> receptionEntity = restTemplate.exchange(getReceptionUrl, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ReceptionResponse>() {
        });
        System.out.println("ReceptionResponse : " + receptionEntity.getBody().getReception());
        if (receptionEntity.getBody().getReception() != null)
        {
           ReceptionPatientJoin recpRespnsForJoin= receptionService.joinPatientToReception(patientEntity.getBody().getPatient(),
                    receptionEntity.getBody().getReception());
            model.addAttribute("recpsRespnForJoin",recpRespnsForJoin);
        }
        else {
            model.addAttribute("recpsRespnForJoin",null);
        }
        return "receptionsList";
    }

}
