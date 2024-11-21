package com.his.client_side.controller;

import com.his.client_side.dto.PatientDto;
import com.his.client_side.response.patient.PatientResponse;
import com.his.client_side.response.reception.ReceptionResponse;
import com.his.client_side.service.CommonService;
import com.his.client_side.service.ReceptionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
    @Autowired
    private ReceptionService receptionService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private RestTemplate restTemplate;
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }



    @GetMapping("/getPatient/{nationalId}")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> getPatient(@PathVariable(value = "nationalId") String nationalId,
                                                         Authentication authentication, Model model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oAuth2AuthenticationToken= (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService.
                loadAuthorizedClient(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(),
                        oAuth2AuthenticationToken.getName());
        String jwtAccessToken = oAuth2AuthorizedClient.getAccessToken().getTokenValue();


        String patientMsUri = "http://localhost:9096/api/patientQueries/byNationalId/"+nationalId;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<PatientResponse> patientResponseEntity = restTemplate.exchange(patientMsUri, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<PatientResponse>() {
        });

        Map<String,Object> map=new HashMap<>();
        if(patientResponseEntity.getBody().getPatient() != null)
        {
            String receptionMsUri = "http://localhost:9096/api/receptionQueries/openReception/byPatientId/"+
                    patientResponseEntity.getBody().getPatient().getPatientId();
            ResponseEntity<ReceptionResponse> receptionResponseEntity = restTemplate.exchange(receptionMsUri, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<ReceptionResponse>() {
            });
            map.put("receptionResponseEntity",receptionResponseEntity.getBody());
        }

        map.put("patientResponseEntity",patientResponseEntity.getBody());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/patientFrom")
    public String getReceptionView(Model model,Authentication authentication,
                                   @AuthenticationPrincipal OidcUser principle,
                                   HttpServletRequest request) throws ServletException {
        String jwtAccessToken= commonService.getJWT(authentication);
        boolean isAuthorized= commonService.checkRoles(jwtAccessToken,"receptionist");
        if(!isAuthorized){
            return "unAuthorized";
        }

        PatientDto patientDto = new PatientDto();
        model.addAttribute("patientDto", patientDto);
        return "patient";
    }

    @PostMapping("/savePatient")
    public String savePatient(@Valid @ModelAttribute("patientDto") PatientDto patientDto,
                              BindingResult result, Model model, Authentication authentication,
                              @AuthenticationPrincipal OidcUser principle,
                              RedirectAttributes attributes){
        if(!result.hasErrors()) {
            String jwtAccessToken = commonService.getJWT(authentication);
            String receptionMsUri = "http://localhost:9096/patient";
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);


           PatientDto patientDtoRest= receptionService.mapPatientDtoRest(patientDto);

            HttpEntity<?> httpEntity = new HttpEntity<>(patientDto, httpHeaders);
            ResponseEntity<PatientResponse> responseEntity = restTemplate.exchange(receptionMsUri, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<PatientResponse>() {
            });
            model.addAttribute("statusCode",responseEntity.getStatusCode().value());
            model.addAttribute("message",responseEntity.getBody().getMessage());
            model.addAttribute("patientId",responseEntity.getBody().getPatient().getPatientId());
            attributes.addAttribute("patientIdAttr",responseEntity.getBody().getPatient().getPatientId());
            return "redirect:/reception/receptionFrom";
        }

        return "patient";
    }

}
