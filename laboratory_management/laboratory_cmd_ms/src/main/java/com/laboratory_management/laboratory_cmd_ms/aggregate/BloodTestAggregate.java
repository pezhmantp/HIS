package com.laboratory_management.laboratory_cmd_ms.aggregate;

import com.laboratory_management.laboratory_cmd_ms.command.CreateBloodTestCommand;
import com.laboratory_management.laboratory_cmd_ms.command.DeleteBloodTestCommand;
import com.laboratory_management.laboratory_cmd_ms.command.UpdateBloodTestCommand;
import com.laboratory_management.laboratory_cmd_ms.service.LabCmdService;
import com.laboratory_management.laboratory_core.event.BloodTestRemovedEvent;
import com.laboratory_management.laboratory_core.event.BloodTestUpdatedEvent;
import com.laboratory_management.laboratory_core.event.NewBloodTestCreatedEvent;
import com.laboratory_management.laboratory_core.model.BloodTest;
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
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Aggregate
public class BloodTestAggregate {

    private static final Logger log= LoggerFactory.getLogger(BloodTestAggregate.class);

    @AggregateIdentifier
    private String id;
    private BloodTest bloodTest;
    @Autowired
    private LabCmdService labCmdService;

    @CommandHandler
    public BloodTestAggregate(CreateBloodTestCommand command) {
        log.info("BloodTestAggregate() -> command received: " + command.toString());
        BloodTest bloodTest = command.getBloodTest();
        NewBloodTestCreatedEvent event =new NewBloodTestCreatedEvent();
        event.setId(command.getId());
        event.setBloodTest(bloodTest);
        AggregateLifecycle.apply(event);
        log.info("BloodTestAggregate() -> NewBloodTestCreatedEvent published: " + event.toString());
    }
    @EventSourcingHandler
    public void on(NewBloodTestCreatedEvent event) {
        this.id = event.getId();
        this.bloodTest = event.getBloodTest();
    }

    @CommandHandler
    public void handle(UpdateBloodTestCommand command) {
        log.info("handle(UpdateBloodTestCommand command) -> command received: " + command.toString());
        BloodTest updatedBloodTest = command.getBloodTest();
        updatedBloodTest.setTestId(command.getBloodTest().getTestId());
        BloodTestUpdatedEvent event = new BloodTestUpdatedEvent();
        event.setId(command.getId());
        event.setBloodTest(updatedBloodTest);
        AggregateLifecycle.apply(event);
        log.info("handle(UpdateBloodTestCommand command) -> BloodTestUpdatedEvent published: " + event.toString());
    }
    @EventSourcingHandler
    public void on(BloodTestUpdatedEvent event) {
        this.id = event.getId();
        this.bloodTest = event.getBloodTest();
    }
    @CommandHandler
    public void handle(DeleteBloodTestCommand command) {
        log.info("handle(DeleteBloodTestCommand command) -> command received: " + command.toString());
        BloodTestRemovedEvent event = new BloodTestRemovedEvent();
        event.setId(command.getId());
        AggregateLifecycle.apply(event);
        log.info("handle(RemovePatientCmd command) -> ReceptionRemovedEvent published: " + event.toString());

    }

    @EventSourcingHandler
    public void on(BloodTestRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }

}
