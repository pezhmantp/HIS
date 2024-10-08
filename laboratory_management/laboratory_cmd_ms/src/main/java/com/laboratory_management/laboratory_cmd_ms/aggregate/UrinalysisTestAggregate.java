package com.laboratory_management.laboratory_cmd_ms.aggregate;

import com.laboratory_management.laboratory_cmd_ms.command.CreateUrinalysisTestCommand;
import com.laboratory_management.laboratory_cmd_ms.command.DeleteUrinalysisTestCommand;
import com.laboratory_management.laboratory_cmd_ms.command.UpdateUrinalysisTestCommand;
import com.laboratory_management.laboratory_core.event.NewUrinalysisTestCreatedEvent;
import com.laboratory_management.laboratory_core.event.UrinalysisTestRemovedEvent;
import com.laboratory_management.laboratory_core.event.UrinalysisTestUpdatedEvent;
import com.laboratory_management.laboratory_core.model.UrinalysisTest;
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
public class UrinalysisTestAggregate {
    @AggregateIdentifier
    private String id;
    private UrinalysisTest urinalysisTest;
    private static final Logger log= LoggerFactory.getLogger(UrinalysisTestAggregate.class);

    @CommandHandler
    public UrinalysisTestAggregate(CreateUrinalysisTestCommand command) {
        log.info("UrinalysisTestAggregate() -> command received: " + command.toString());
        UrinalysisTest urinalysisTest = command.getUrinalysisTest();
        NewUrinalysisTestCreatedEvent event =new NewUrinalysisTestCreatedEvent();
        event.setId(command.getId());
        event.setUrinalysisTest(urinalysisTest);
        AggregateLifecycle.apply(event);
        log.info("UrinalysisTestAggregate() -> NewUrinalysisTestCreatedEvent published: " + event.toString());
    }
    @EventSourcingHandler
    public void on(NewUrinalysisTestCreatedEvent event) {
        this.id = event.getId();
        this.urinalysisTest = event.getUrinalysisTest();
    }
    @CommandHandler
    public void handle(UpdateUrinalysisTestCommand command) {
        log.info("handle(UpdateUrinalysisTestCommand command) -> command received: " + command.toString());
        UrinalysisTest updatedUrinalysisTest = command.getUrinalysisTest();
        updatedUrinalysisTest.setTestId(command.getUrinalysisTest().getTestId());
        UrinalysisTestUpdatedEvent event = new UrinalysisTestUpdatedEvent();
        event.setId(command.getId());
        event.setUrinalysisTest(updatedUrinalysisTest);
        AggregateLifecycle.apply(event);
        log.info("handle(UpdateUrinalysisTestCommand command) -> UrinalysisTestUpdatedEvent published: " + event.toString());
    }
    @EventSourcingHandler
    public void on(UrinalysisTestUpdatedEvent event) {
        this.id = event.getId();
        this.urinalysisTest = event.getUrinalysisTest();
    }
    @CommandHandler
    public void handle(DeleteUrinalysisTestCommand command) {
        log.info("handle(DeleteBloodTestCommand command) -> command received: " + command.toString());
        UrinalysisTestRemovedEvent event = new UrinalysisTestRemovedEvent();
        event.setId(command.getId());
        AggregateLifecycle.apply(event);
        log.info("handle(DeleteUrinalysisTestCommand command) -> UrinalysisTestRemovedEvent published: " + event.toString());
    }
    @EventSourcingHandler
    public void on(UrinalysisTestRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
