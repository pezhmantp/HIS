package com.patient_management.patient_cmd_ms.service;

import com.patient_management.patient_core.model.Patient;
import com.patient_management.patient_core.repository.PatientRepository;
import com.patient_management.patient_core.responeObj.PatientResponse;
import com.patient_management.patient_cmd_ms.dto.PatientDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private static final Logger log= LoggerFactory.getLogger(PatientServiceImpl.class);

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public String generatePatientId() {
        log.info("PatientService -> generatePatientId(): generate a unique patientId ");
        String RNDCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        while (sb.length() < 12) {
            int index = (int) (rnd.nextFloat() * RNDCHARS.length());
            sb.append(RNDCHARS.charAt(index));
        }
        String rndStr = sb.toString();
        return rndStr;
    }

    public Patient mapPatientDtoToPatient(PatientDto patientDto) // serv
    {
        if(patientDto == null)
        {
            throw new NullPointerException("PatientDto should not be null");
        }
        Patient patient=new Patient();
        patient.setDob(patientDto.getDob());
        patient.setPhones(patientDto.getPhones());
        patient.setNationalId(patientDto.getNationalId());
        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setEmail(patientDto.getEmail());
        patient.setAge(patientDto.getAge());
        patient.setAddress(patientDto.getAddress());
        patient.setGender(patientDto.getGender());
        return patient;
    }

    @Override
    public PatientResponse fetchPatient(String nationalId) {
        Patient patient = patientRepository.findByNationalId(nationalId);
        if (patient == null)
        {
            return new PatientResponse(null,"Patient does not exist!");

        }
        return new PatientResponse(patient,"Patient exists!");

    }
    @Override
    public Patient fetchPatient2(String nationalId) {
        Patient patient = patientRepository.findByNationalId(nationalId);
        if (patient == null)
        {
            return null;
        }
        return patient;
    }
}
