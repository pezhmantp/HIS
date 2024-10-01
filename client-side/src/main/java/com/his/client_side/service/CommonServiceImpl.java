package com.his.client_side.service;

import com.his.client_side.model.patient.Patient;
import com.his.client_side.model.reception.Reception;
import com.his.client_side.response.reception.ReceptionPatientJoin;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService{

    @Autowired
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Override
    public String getJWT(Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oAuth2AuthenticationToken= (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService.
                loadAuthorizedClient(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(),
                        oAuth2AuthenticationToken.getName());
        String jwtAccessToken = oAuth2AuthorizedClient.getAccessToken().getTokenValue();
        return jwtAccessToken;
    }
    @Override
    public boolean checkRoles(String jwtToken,String roleName){

        String[] split_string = jwtToken.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];
        Base64 base64Url = new Base64(true);
        String header = new String(base64Url.decode(base64EncodedHeader));
        String body = new String(base64Url.decode(base64EncodedBody));
        int startIndex= body.indexOf("realm_access") + 24;
        int i=startIndex;
        String splitedToken= "";

        while (body.charAt(i) != ']')
        {
            splitedToken = splitedToken + body.charAt(i);
            i++;
        }
        splitedToken = splitedToken.replaceAll("\"","");
        String [] roles = splitedToken.split(",");

        boolean isAuthorized=false;
        for (String role : roles) {
            if(role.equals(roleName)){
                isAuthorized = true;
            }
        }
        return isAuthorized;
    }

    @Override
    public List<ReceptionPatientJoin> joinPatientsToReceptions(List<Patient> patients, List<Reception> receptions) {
        List<ReceptionPatientJoin> recpRespnsForJoinList = new ArrayList<>();
        patients.forEach(patient ->{
            receptions.forEach(reception -> {
                if (reception.getPatientId().equals(patient.getPatientId())){
                    recpRespnsForJoinList.add(new ReceptionPatientJoin(reception.getReceptionId(),reception.getPatientId(),
                            patient.getFirstName(),patient.getLastName(),patient.getNationalId(),reception.getEmergency(),
                            null,reception.getVisitStatus(),reception.getReceptionStatus(),
                            reception.getDateOfReception(),reception.getVitalSign(),patient.getDob(),patient.getGender(),reception.getDescription()));
                }
            });
        });
        System.out.println("recpRespnsForJoinList : " + recpRespnsForJoinList.toString());
        return recpRespnsForJoinList;
    }

}
