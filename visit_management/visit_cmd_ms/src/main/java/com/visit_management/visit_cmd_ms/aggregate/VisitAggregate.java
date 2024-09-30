package com.visit_management.visit_cmd_ms.aggregate;

import com.visit_management.visit_cmd_ms.command.CreateVisitCommand;
import com.visit_management.visit_cmd_ms.command.RemoveVisitCommand;
import com.visit_management.visit_cmd_ms.command.UpdateVisitCommand;
import com.visit_management.visit_core.event.NewVisitCreatedEvent;
import com.visit_management.visit_core.event.VisitRemovedEvent;
import com.visit_management.visit_core.event.VisitUpdatedEvent;
import com.visit_management.visit_core.model.Visit;
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
public class VisitAggregate {
    @AggregateIdentifier
    private String id;
    private Visit visit;
    private static final Logger log= LoggerFactory.getLogger(VisitAggregate.class);

    @CommandHandler
    public VisitAggregate(CreateVisitCommand command) {
        log.info("VisitAggregate(CreateVisitCommand command) -> command received: " + command.toString());
        Visit visit=command.getVisit();
        NewVisitCreatedEvent event=new NewVisitCreatedEvent();
        event.setId(command.getId());
        event.setVisit(command.getVisit());
        AggregateLifecycle.apply(event);
        log.info("VisitAggregate(CreateVisitCommand command) -> NewVisitCreatedEvent published: " + event.toString());
    }
    @EventSourcingHandler
    public void on(NewVisitCreatedEvent event) {
        this.id = event.getId();
        this.visit = event.getVisit();
    }
    @CommandHandler
    public void handle(UpdateVisitCommand command) {
        log.info("handle(UpdateVisitCommand command) -> command received: " + command.toString());
        Visit updatedVisit = command.getVisit();
        updatedVisit.setVisitId(command.getId());
        VisitUpdatedEvent event = new VisitUpdatedEvent();
        event.setId(command.getId());
        event.setVisit(updatedVisit);
        AggregateLifecycle.apply(event);
        log.info("handle(UpdateVisitCommand command) -> VisitUpdatedEvent published: " + event.toString());
    }
    @EventSourcingHandler
    public void on(VisitUpdatedEvent event) {
        this.id = event.getId();
        this.visit = event.getVisit();
    }
    @CommandHandler
    public void handle(RemoveVisitCommand command) {
        log.info("handle(RemoveVisitCommand command) -> command received: " + command.toString());
        VisitRemovedEvent event = new VisitRemovedEvent();
        event.setId(command.getId());
        AggregateLifecycle.apply(event);
        log.info("handle(VisitRemovedEvent command) -> VisitRemovedEvent published: " + event.toString());
    }
    @EventSourcingHandler
    public void on(VisitRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
