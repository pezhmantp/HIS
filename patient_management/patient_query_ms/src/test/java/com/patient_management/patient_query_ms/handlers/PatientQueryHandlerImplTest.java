package com.patient_management.patient_query_ms.handlers;

import com.patient_management.patient_core.model.Address;
import com.patient_management.patient_core.model.Patient;
import com.patient_management.patient_core.model.Phone;
import com.patient_management.patient_core.queries.FindPatientByNationalIdQuery;
import com.patient_management.patient_core.repository.PatientRepository;
import com.patient_management.patient_core.responeObj.PatientResponse;
import org.dozer.DozerBeanMapper;
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

class PatientQueryHandlerImplTest {

    @InjectMocks
    private PatientQueryHandlerImpl patientQueryHandler;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private DozerBeanMapper mapper;
    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("Should find patient by national Id and return patient response")
    void shouldFindPatientByNationalId() {
        Long ms=System.currentTimeMillis();
        List<Phone> phones=new ArrayList<>();
        Phone phone1=new Phone(1L,"9999");
        Phone phone2=new Phone(2L,"8888");
        phones.add(phone1);
        phones.add(phone2);
        Patient expectedPatient=new Patient("123","patientFirstName",
                "patientLastName",new Date(ms),"patientNid","MAN",
                33,"email",new Address(1L,"province","city",
                "1234","-","A1"),phones);
        FindPatientByNationalIdQuery query=new FindPatientByNationalIdQuery("nationalId");
        Mockito.when(patientRepository.findByNationalId(query.getNationalId())).thenReturn(expectedPatient);

        PatientResponse fetchedPatient= patientQueryHandler.findPatientByNationalId(query);

        Assertions.assertNotNull(fetchedPatient.getPatient());
        Assertions.assertEquals("Patient exists!",fetchedPatient.getMessage());
        Assertions.assertEquals(expectedPatient.getNationalId(),fetchedPatient.getPatient().getNationalId());
    }
    @Test
    @DisplayName("Should not find any patient by the given nationalId and return patient response with null value for patient")
    void shouldNotFindPatientByNationalId() {
        Long ms=System.currentTimeMillis();
        List<Phone> phones=new ArrayList<>();
        Phone phone1=new Phone(1L,"9999");
        Phone phone2=new Phone(2L,"8888");
        phones.add(phone1);
        phones.add(phone2);
        Patient expectedPatient=new Patient("123","patientFirstName",
                "patientLastName",new Date(ms),"patientNid","MAN",
                33,"email",new Address(1L,"province","city",
                "1234","-","A1"),phones);
        FindPatientByNationalIdQuery query=new FindPatientByNationalIdQuery("incorrectNationalId");
        Mockito.when(patientRepository.findByNationalId("patientNid")).thenReturn(expectedPatient);

        PatientResponse fetchedPatient= patientQueryHandler.findPatientByNationalId(query);

        Assertions.assertNull(fetchedPatient.getPatient());
        Assertions.assertEquals("Patient not found",fetchedPatient.getMessage());
    }
}