package com.patient_management.patient_query_ms.handlers;

import com.patient_management.patient_core.event.NewPatientCreatedEvent;
import com.patient_management.patient_core.event.PatientRemovedEvent;
import com.patient_management.patient_core.event.PatientUpdatedEvent;
import com.patient_management.patient_core.model.Address;
import com.patient_management.patient_core.model.Patient;
import com.patient_management.patient_core.model.Phone;
import com.patient_management.patient_core.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatientCmdHandlerImplTest {

    @InjectMocks
    private PatientCmdHandlerImpl patientCmdHandler;
    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Captor
    private ArgumentCaptor<Patient> patientArgumentCaptor;
    @Test
    @DisplayName("should save the new patient on receiving NewPatientCreatedEvent")
    void onNewPatientCreatedEvent() {
        Long ms=System.currentTimeMillis();
        List<Phone> phones=new ArrayList<>();
        Phone phone1=new Phone(1L,"9999");
        Phone phone2=new Phone(2L,"8888");
        phones.add(phone1);
        phones.add(phone2);
        Patient patient=new Patient("123","patientFirstName",
                "patientLastName",new Date(ms),"patientNid","MAN",
                33,"email",new Address(1L,"province","city",
                "1234","-","A1"),phones);
        NewPatientCreatedEvent event=new NewPatientCreatedEvent("123",patient);
        patientCmdHandler.onNewPatientCreatedEvent(event);

        Mockito.verify(patientRepository,Mockito.times(1)).save(patientArgumentCaptor.capture());

        Assertions.assertEquals(patient.getPatientId(),patientArgumentCaptor.getValue().getPatientId());
        Assertions.assertEquals(patient.getAge(),patientArgumentCaptor.getValue().getAge());
        Assertions.assertEquals(patient.getGender(),patientArgumentCaptor.getValue().getGender());
        Assertions.assertEquals(patient.getEmail(),patientArgumentCaptor.getValue().getEmail());
        Assertions.assertEquals(patient.getAddress(),patientArgumentCaptor.getValue().getAddress());
        Assertions.assertEquals(patient.getFirstName(),patientArgumentCaptor.getValue().getFirstName());
        Assertions.assertEquals(patient.getLastName(),patientArgumentCaptor.getValue().getLastName());
        Assertions.assertEquals(patient.getNationalId(),patientArgumentCaptor.getValue().getNationalId());
        Assertions.assertEquals(patient.getDob(),patientArgumentCaptor.getValue().getDob());
        Assertions.assertEquals(patient.getPhones(),patientArgumentCaptor.getValue().getPhones());
    }


    @Test
    @DisplayName("should save the patient on receiving PatientUpdatedEvent")
    void onPatientUpdatedEvent() {
        Long ms=System.currentTimeMillis();
        List<Phone> phones=new ArrayList<>();
        Phone phone1=new Phone(1L,"9999-updated");
        Phone phone2=new Phone(2L,"8888-updated");
        phones.add(phone1);
        phones.add(phone2);
        Patient patient=new Patient("123","patientFirstName",
                "patientLastName",new Date(ms),"patientNid","MAN",
                33,"email-updated",new Address(1L,"province-updated","city-updated",
                "1234-updated","-","A1"),phones);
        PatientUpdatedEvent event=new PatientUpdatedEvent("123",patient);
        patientCmdHandler.onPatientUpdatedEvent(event);

        Mockito.verify(patientRepository,Mockito.times(1)).save(patientArgumentCaptor.capture());


        Assertions.assertEquals(patient.getPatientId(),patientArgumentCaptor.getValue().getPatientId());
        Assertions.assertEquals(patient.getAge(),patientArgumentCaptor.getValue().getAge());
        Assertions.assertEquals(patient.getGender(),patientArgumentCaptor.getValue().getGender());
        Assertions.assertEquals(patient.getEmail(),patientArgumentCaptor.getValue().getEmail());
        Assertions.assertEquals(patient.getAddress(),patientArgumentCaptor.getValue().getAddress());
        Assertions.assertEquals(patient.getFirstName(),patientArgumentCaptor.getValue().getFirstName());
        Assertions.assertEquals(patient.getLastName(),patientArgumentCaptor.getValue().getLastName());
        Assertions.assertEquals(patient.getNationalId(),patientArgumentCaptor.getValue().getNationalId());
        Assertions.assertEquals(patient.getDob(),patientArgumentCaptor.getValue().getDob());
        Assertions.assertEquals(patient.getPhones(),patientArgumentCaptor.getValue().getPhones());
    }

    @Test
    @DisplayName("should delete the patient on receiving PatientRemovedEvent")
    void onPatientRemovedEvent() {
        PatientRemovedEvent event=new PatientRemovedEvent("1234");
        Mockito.doNothing().when(patientRepository).deleteById(event.getId());
        patientCmdHandler.onPatientRemovedEvent(event);

        Mockito.verify(patientRepository,Mockito.times(1)).deleteById("1234");
    }
}