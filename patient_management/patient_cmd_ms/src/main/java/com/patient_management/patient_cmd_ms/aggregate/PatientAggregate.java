package com.patient_management.patient_cmd_ms.aggregate;

import com.patient_management.patient_core.event.NewPatientCreatedEvent;
import com.patient_management.patient_core.event.PatientRemovedEvent;
import com.patient_management.patient_core.event.PatientUpdatedEvent;
import com.patient_management.patient_core.model.Patient;
import com.patient_management.patient_cmd_ms.command.NewPatientCmd;
import com.patient_management.patient_cmd_ms.command.RemovePatientCmd;
import com.patient_management.patient_cmd_ms.command.UpdatePatientCmd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Aggregate
public class PatientAggregate {
    private static final Logger log= LoggerFactory.getLogger(PatientAggregate.class);
    @AggregateIdentifier
    private String id;
    private Patient patient;


    @CommandHandler
    public PatientAggregate(NewPatientCmd command) {
        log.info("PatientAggregate(NewPatientCmd command) -> command received: " + command.toString());
        Patient patient = command.getPatient();
        NewPatientCreatedEvent event = new NewPatientCreatedEvent();
        event.setId(command.getId());
        event.setPatient(patient);
        AggregateLifecycle.apply(event);
        log.info("PatientAggregate(NewPatientCmd command) -> NewPatientCreatedEvent published: " + event.toString());
    }

    @EventSourcingHandler
    public void on(NewPatientCreatedEvent event) {
        this.id = event.getId();
        this.patient = event.getPatient();
    }

    @CommandHandler
    public void handle(UpdatePatientCmd command) {
        log.info("handle(UpdatePatientCmd command) -> command received: " + command.toString());
        Patient updatedPatient = command.getPatient();
        updatedPatient.setPatientId(command.getId());
        PatientUpdatedEvent event = new PatientUpdatedEvent();
        event.setId(command.getId());
        event.setPatient(updatedPatient);
        System.out.println("Agg: event : " + event);
        AggregateLifecycle.apply(event);
        log.info("handle(UpdatePatientCmd command) -> PatientUpdatedEvent published: " + event.toString());
    }

    @EventSourcingHandler
    public void on(PatientUpdatedEvent event) {
        this.id = event.getId();
        this.patient = event.getPatient();
    }

    @CommandHandler
    public void handle(RemovePatientCmd command) {
        log.info("handle(RemovePatientCmd command) -> command received: " + command.toString());
        PatientRemovedEvent event = new PatientRemovedEvent(command.getId());
        AggregateLifecycle.apply(event);
        log.info("handle(RemovePatientCmd command) -> PatientRemovedEvent published: " + event.toString());
    }

    @EventSourcingHandler
    public void on(PatientRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
