package com.his.client_side.service;

import com.his.client_side.dto.PatientDto;
import com.his.client_side.model.doctor.Doctor;
import com.his.client_side.model.patient.Address;
import com.his.client_side.model.patient.Patient;
import com.his.client_side.model.reception.Reception;
import com.his.client_side.repository.DoctorRepository;
import com.his.client_side.response.patient.PatientsResponse;
import com.his.client_side.response.reception.ReceptionPatientJoin;
import com.his.client_side.response.reception.ReceptionsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReceptionServiceImpl implements ReceptionService{
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
    @Override
    public PatientDto mapPatientDtoRest(PatientDto patientDto) {
        Address address = new Address(patientDto.getAddress().getProvince(), patientDto.getAddress().getCity(),
                patientDto.getAddress().getPostalCode(), patientDto.getAddress().getRestOfAddress(), "");

        PatientDto patientDtoRest = new PatientDto();
        patientDtoRest.setPhones(Arrays.asList(patientDto.getPhones().get(0)));
        patientDtoRest.setAddress(address);
        patientDtoRest.setAge(null);
        patientDtoRest.setEmail(patientDto.getEmail());
        patientDtoRest.setFirstName(patientDto.getFirstName());
        patientDtoRest.setLastName(patientDto.getLastName());
        patientDtoRest.setNationalId(patientDto.getNationalId());
        patientDtoRest.setGender(patientDto.getGender());
        patientDtoRest.setDob(patientDto.getDob());
        return patientDtoRest;
    }

    @Override
    public List<Doctor> getDoctors() {
       return doctorRepository.findAll();
    }

    @Override
    public PatientsResponse getPatientsByPatientIds(ResponseEntity<ReceptionsResponse> responseEntity, HttpEntity<?> httpEntity) {
        String patientQueryUri = "http://localhost:8082/api/patientQueries/ListOfPatientsInfo";
        List<String> patientsIds=new ArrayList<>();
        responseEntity.getBody().getReceptions().forEach(i->
                patientsIds.add(i.getPatientId())
        );
        URI uri = UriComponentsBuilder
                .fromUri(URI.create(patientQueryUri))
                .queryParam("patientsIds", patientsIds)
                .build()
                .toUri();
        PatientsResponse patientsList= restTemplate.exchange(uri , HttpMethod.GET, httpEntity, new ParameterizedTypeReference<PatientsResponse>(){}).getBody();

        return patientsList;
    }


    @Override
    public ReceptionPatientJoin joinPatientToReception(Patient patient, Reception reception) {
        ReceptionPatientJoin recpRespnsForJoin=new ReceptionPatientJoin();
        recpRespnsForJoin.setReceptionId(reception.getReceptionId());
        recpRespnsForJoin.setReceptionStatus(reception.getReceptionStatus());
        recpRespnsForJoin.setEmergency(reception.getEmergency());
        recpRespnsForJoin.setFirstName(patient.getFirstName());
        recpRespnsForJoin.setLastName(patient.getLastName());
        recpRespnsForJoin.setNationalId(patient.getNationalId());
        recpRespnsForJoin.setVisitStatus(reception.getVisitStatus());
        recpRespnsForJoin.setDob(patient.getDob());
        recpRespnsForJoin.setGender(patient.getGender());
        recpRespnsForJoin.setDescription(reception.getDescription());
        recpRespnsForJoin.setVitalSign(reception.getVitalSign());
        return recpRespnsForJoin;
    }

    @Override
    public Boolean validateJWTExpirationTime(Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oAuth2AuthenticationToken= (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService.
                loadAuthorizedClient(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(),
                        oAuth2AuthenticationToken.getName());
        long currentTime = System.currentTimeMillis() / 1000;
        Long issuance= oAuth2AuthorizedClient.getAccessToken().getIssuedAt().getEpochSecond();
        Long exp= oAuth2AuthorizedClient.getAccessToken().getExpiresAt().getEpochSecond();
        Long threshold=exp-issuance;

        if((currentTime-issuance) > threshold)
        {
            return false;
        }
        else {
            return true;
        }

    }

}
