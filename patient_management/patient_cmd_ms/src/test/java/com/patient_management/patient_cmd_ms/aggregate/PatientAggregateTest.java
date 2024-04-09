package com.patient_management.patient_cmd_ms.aggregate;

import com.patient_management.patient_core.event.NewPatientCreatedEvent;
import com.patient_management.patient_core.event.PatientRemovedEvent;
import com.patient_management.patient_core.event.PatientUpdatedEvent;
import com.patient_management.patient_core.model.Address;
import com.patient_management.patient_core.model.Patient;
import com.patient_management.patient_core.model.Phone;
import com.patient_management.patient_cmd_ms.command.NewPatientCmd;
import com.patient_management.patient_cmd_ms.command.RemovePatientCmd;
import com.patient_management.patient_cmd_ms.command.UpdatePatientCmd;
import org.junit.Before;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PatientAggregateTest {
    private FixtureConfiguration<PatientAggregate> fixture;

    @Before
    public void setUp() throws Exception {
       fixture = new AggregateTestFixture<>(PatientAggregate.class);
    }


    @Test
    @DisplayName("NewPatientCmd should produce NewPatientCreatedEvent")
    public void testNewPatientCmd() {
        Patient patient=new Patient();
        patient.setPatientId("patientId");
        patient.setNationalId("nationalId");
        Long ms=System.currentTimeMillis();
        Date date=new Date(ms);
        patient.setDob(date);
        patient.setAge(27);
        patient.setEmail("email");
        patient.setSex("MAN");
        patient.setFirstName("fName");
        patient.setLastName("lName");
        Address address=new Address(1L,"province","city","1234567","","A1");
        Phone phone=new Phone(1L,"1111111111");
        List<Phone> phones=new ArrayList<>();
        phones.add(phone);
        patient.setAddress(address);
        patient.setPhones(phones);
        fixture.given(new NewPatientCreatedEvent(patient.getPatientId(), patient))
                .when(new NewPatientCmd(patient.getPatientId(), patient))
                .expectEvents(new NewPatientCreatedEvent(patient.getPatientId(), patient));

    }
    @Test
    @DisplayName("UpdatePatientCmd should produce PatientUpdatedEvent")
    public void testUpdatePatientCmd() {
        Patient patient=new Patient();
        patient.setPatientId("patientId");
        patient.setNationalId("nationalId");
        Long ms=System.currentTimeMillis();
        Date date=new Date(ms);
        patient.setDob(date);
        patient.setAge(27);
        patient.setEmail("email-updated");
        patient.setSex("MAN");
        patient.setFirstName("fName");
        patient.setLastName("lName");
        Address address=new Address(1L,"province-updated","city-updated","1234567","","A1");
        Phone phone=new Phone(1L,"1111111111");
        List<Phone> phones=new ArrayList<>();
        phones.add(phone);
        patient.setAddress(address);
        patient.setPhones(phones);

        fixture.given(new PatientUpdatedEvent(patient.getPatientId(), patient))
                .when(new UpdatePatientCmd(patient.getPatientId(), patient))
                .expectEvents(new PatientUpdatedEvent(patient.getPatientId(), patient));

    }
    @Test
    @DisplayName("RemovePatientCmd should produce PatientRemovedEvent")
    public void testRemovePatientCmd() {
        Patient patient=new Patient();
        patient.setPatientId("patientId");
        fixture.given(new NewPatientCreatedEvent(patient.getPatientId(), patient))
                .when(new RemovePatientCmd(patient.getPatientId()))
                //.expectSuccessfulHandlerExecution()
                .expectEvents(new PatientRemovedEvent(patient.getPatientId()));
    }
}