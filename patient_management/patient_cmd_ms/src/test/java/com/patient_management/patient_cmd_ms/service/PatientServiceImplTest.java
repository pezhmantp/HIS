package com.patient_management.patient_cmd_ms.service;

import com.patient_management.patient_core.model.Address;
import com.patient_management.patient_core.model.Patient;
import com.patient_management.patient_core.model.Phone;
import com.patient_management.patient_core.repository.PatientRepository;
import com.patient_management.patient_core.responeObj.PatientResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatientServiceImplTest {

    @InjectMocks
    private PatientServiceImpl patientService;

    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should find and return patient by nationalId")
    void shouldFindPatientByNationalId() {
        Long ms=System.currentTimeMillis();
        List<Phone> phones=new ArrayList<>();
        Phone phone1=new Phone(1L,"9999");
        Phone phone2=new Phone(2L,"8888");
        phones.add(phone1);
        phones.add(phone2);
        Patient actualPatient=new Patient("123","patientFirstName",
                "patientLastName",new Date(ms),"patientNid","MAN",
                33,"email",new Address(1L,"province","city",
                "1234","-","A1"),phones);
        Mockito.when(patientRepository.findByNationalId("patientNid")).thenReturn(actualPatient);

        PatientResponse response = patientService.fetchPatient("patientNid");

        Assertions.assertNotNull(response.getPatient());
        Assertions.assertEquals(response.getPatient(),actualPatient);
    }
    @Test
    @DisplayName("should not find any patient by incorrect nationalId")
    void shouldNotFindPatientByIncorrectNationalId() {
        Long ms=System.currentTimeMillis();
        List<Phone> phones=new ArrayList<>();
        Phone phone1=new Phone(1L,"9999");
        Phone phone2=new Phone(2L,"8888");
        phones.add(phone1);
        phones.add(phone2);
        Patient actualPatient=new Patient("123","patientFirstName",
                "patientLastName",new Date(ms),"patientNid","MAN",
                33,"email",new Address(1L,"province","city",
                "1234","-","A1"),phones);
        Mockito.when(patientRepository.findByNationalId("patientNid")).thenReturn(actualPatient);

        PatientResponse response = patientService.fetchPatient("incorrectPatientNid");

        Assertions.assertNull(response.getPatient());
        Assertions.assertNotEquals(response.getPatient(),actualPatient);
    }

    @Test
    @DisplayName("should generate new random patient id with length of 12")
    void shouldGeneratePatientId(){
       String patientId = patientService.generatePatientId();
       System.out.println("patientId " + patientId);
       Assertions.assertNotNull(patientId);
       Assertions.assertTrue(patientId.length() == 12);
    }
}

