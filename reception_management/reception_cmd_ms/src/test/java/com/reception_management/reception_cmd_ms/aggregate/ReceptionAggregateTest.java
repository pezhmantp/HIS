package com.reception_management.reception_cmd_ms.aggregate;

import com.reception_management.reception_cmd_ms.command.CreateReceptionCmd;
import com.reception_management.reception_cmd_ms.command.RemoveReceptionCmd;
import com.reception_management.reception_cmd_ms.command.UpdateReceptionCmd;
import com.reception_management.reception_core.event.NewReceptionCreatedEvent;
import com.reception_management.reception_core.event.ReceptionRemovedEvent;
import com.reception_management.reception_core.event.ReceptionUpdatedEvent;
import com.reception_management.reception_core.model.Reception;
import com.reception_management.reception_core.model.VitalSign;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.sql.Date;

import static org.junit.Assert.*;

public class ReceptionAggregateTest {
    private FixtureConfiguration<ReceptionAggregate> fixture;

    public ReceptionAggregateTest() {
    }

    @Before
    public void setUp() throws Exception {
        this.fixture = new AggregateTestFixture(ReceptionAggregate.class);
    }

    @Test
    @DisplayName("CreateReceptionCmd should publish NewReceptionCreatedEvent")
    public void testCreateReceptionCmd() {
        Reception reception = new Reception();
        reception.setReceptionId("receptionId");
        reception.setPatientId("123");
        Long ms = System.currentTimeMillis();
        Date date = new Date(ms);
        reception.setDateOfReception(date);
        reception.setEmergency(false);
        reception.setDescription("comment");
        reception.setDoctorId("doctorId");
        reception.setVitalSign(new VitalSign(1L, 13.5, 7.5, 35.5, 93.2));
        this.fixture.given(new NewReceptionCreatedEvent(reception.getReceptionId(), reception))
                .when(new CreateReceptionCmd(reception.getReceptionId(), reception))
                .expectEvents(new NewReceptionCreatedEvent(reception.getReceptionId(), reception));
    }

    @Test
    @DisplayName("UpdateReceptionCmd should publish ReceptionUpdatedEvent")
    public void testUpdateReceptionCmd() {
        Reception reception = new Reception();
        reception.setReceptionId("receptionId");
        reception.setPatientId("123");
        Long ms = System.currentTimeMillis();
        Date date = new Date(ms);
        reception.setDateOfReception(date);
        reception.setEmergency(false);
        reception.setDescription("comment");
        reception.setDoctorId("doctorId");
        reception.setVitalSign(new VitalSign(1L, 13.5, 7.5, 35.5, 93.2));
        this.fixture.given(new ReceptionUpdatedEvent(reception.getReceptionId(), reception))
                .when(new UpdateReceptionCmd(reception.getReceptionId(), reception))
                .expectEvents(new ReceptionUpdatedEvent(reception.getReceptionId(), reception));
    }

    @Test
    @DisplayName("RemoveReceptionCmd should publish ReceptionRemovedEvent")
    public void testRemovePatientCmd() {
        Reception reception = new Reception();
        reception.setReceptionId("receptionId");
        this.fixture.given(new NewReceptionCreatedEvent(reception.getReceptionId(), reception))
                .when(new RemoveReceptionCmd(reception.getReceptionId()))
                .expectEvents(new ReceptionRemovedEvent(reception.getReceptionId()));
    }
}