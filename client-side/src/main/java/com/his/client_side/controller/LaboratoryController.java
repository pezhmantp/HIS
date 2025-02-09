package com.his.client_side.controller;

import com.his.client_side.dto.BloodTestDto;
import com.his.client_side.dto.UrinalysisTestDto;
import com.his.client_side.response.laboratory.TestResponses;
import com.his.client_side.service.CommonService;
import com.his.client_side.service.ReceptionService;
import com.his.client_side.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
@RequestMapping("/laboratory")
public class LaboratoryController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CommonService commonService;
    @Autowired
    private VisitService visitService;
    @Autowired
    private ReceptionService receptionService;

    @GetMapping("/laboratoryPage")
    public String laboratoryPage(Model model, Authentication authentication){
        String jwtAccessToken= commonService.getJWT(authentication);
        boolean isAuthorized= commonService.checkRoles(jwtAccessToken,"lab_technician");
        if(!isAuthorized){
            return "unAuthorized";
        }
        String allReceptionQueryUri = "http://localhost:9096/laboratoryQuery/getOpenTests";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<TestResponses> responseEntity = restTemplate.exchange(allReceptionQueryUri, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
        });
        BloodTestDto bloodTestDto=new BloodTestDto();
        UrinalysisTestDto urinalysisTestDto = new UrinalysisTestDto();
        model.addAttribute("bloodTestDto",bloodTestDto);
        model.addAttribute("urinalysisTestDto",urinalysisTestDto);
        model.addAttribute("tests",responseEntity.getBody().getTestResponses());
        return "laboratory";
    }
    @PostMapping("/saveBloodTest")
    public String saveBloodTest(Model model, @ModelAttribute("bloodTestDto") BloodTestDto bloodTestDto, Authentication authentication){
        bloodTestDto.setStatus("ready");
        bloodTestDto.setType("bloodTestDto");
        String laboratoryCmdUri = "http://localhost:9096/laboratoryCmd/test";
        String jwtAccessToken = commonService.getJWT(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(bloodTestDto,httpHeaders);
//
        ResponseEntity<Map<String,String>> responseEntity = restTemplate.exchange(laboratoryCmdUri, HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<>() {
        });

        return "redirect:/laboratory/laboratoryPage";
    }
    @PostMapping("/saveUrinalysisTest")
    public String saveUrinalysisTest(Model model, @ModelAttribute("urinalysisTestDto") UrinalysisTestDto urinalysisTestDto,Authentication authentication){
        urinalysisTestDto.setStatus("ready");
        urinalysisTestDto.setType("urinalysisTestDto");
        String laboratoryCmdUri = "http://localhost:9096/laboratoryCmd/test";
        String jwtAccessToken = commonService.getJWT(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtAccessToken);
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(urinalysisTestDto,httpHeaders);
//
        ResponseEntity<Map<String,String>> responseEntity = restTemplate.exchange(laboratoryCmdUri, HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<>() {
        });

        return "redirect:/laboratory/laboratoryPage";
    }
}
