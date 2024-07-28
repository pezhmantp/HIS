package com.reception_management.reception_cmd_ms.aggregate;

import com.reception_management.reception_cmd_ms.command.CreateReceptionCmd;
import com.reception_management.reception_cmd_ms.command.RemoveReceptionCmd;
import com.reception_management.reception_cmd_ms.command.UpdateReceptionCmd;
import com.reception_management.reception_core.event.NewReceptionCreatedEvent;
import com.reception_management.reception_core.event.ReceptionRemovedEvent;
import com.reception_management.reception_core.event.ReceptionUpdatedEvent;
import com.reception_management.reception_core.model.Reception;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class ReceptionAggregate {
    @AggregateIdentifier
    private String id;
    private Reception reception;

    private static final Logger log= LoggerFactory.getLogger(ReceptionAggregate.class);

    @CommandHandler
    public ReceptionAggregate(CreateReceptionCmd command) {
        log.info("ReceptionAggregate(CreateReceptionCmd command) -> command received: " + command.toString());
        Reception reception = command.getReception();
        NewReceptionCreatedEvent event =new NewReceptionCreatedEvent();
        event.setId(command.getId());
        event.setReception(reception);
        AggregateLifecycle.apply(event);
        log.info("ReceptionAggregate(CreateReceptionCmd command) -> NewReceptionCreatedEvent published: " + event.toString());
    }

    @EventSourcingHandler
    public void on(NewReceptionCreatedEvent event) {
        this.id = event.getId();
        this.reception = event.getReception();
    }

    @CommandHandler
    public void handle(UpdateReceptionCmd command) {
        log.info("handle(UpdateReceptionCmd command) -> command received: " + command.toString());
        Reception updatedReception = command.getReception();
        updatedReception.setPatientId(command.getReception().getPatientId());
        ReceptionUpdatedEvent event = new ReceptionUpdatedEvent();
        event.setId(command.getId());
        event.setReception(updatedReception);
        AggregateLifecycle.apply(event);
        log.info("handle(UpdateReceptionCmd command) -> ReceptionUpdatedEvent published: " + event.toString());
    }

    @EventSourcingHandler
    public void on(ReceptionUpdatedEvent event) {
        this.id = event.getId();
        this.reception = event.getReception();
    }

    @CommandHandler
    public void handle(RemoveReceptionCmd command) {
        log.info("handle(RemoveReceptionCmd command) -> command received: " + command.toString());
        ReceptionRemovedEvent event = new ReceptionRemovedEvent();
        event.setId(command.getId());
        AggregateLifecycle.apply(event);
        log.info("handle(RemovePatientCmd command) -> ReceptionRemovedEvent published: " + event.toString());
    }

    @EventSourcingHandler
    public void on(ReceptionRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
