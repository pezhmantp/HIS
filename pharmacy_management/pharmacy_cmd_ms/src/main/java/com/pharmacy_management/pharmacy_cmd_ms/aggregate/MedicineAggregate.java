package com.pharmacy_management.pharmacy_cmd_ms.aggregate;

import com.pharmacy_management.pharmacy_cmd_ms.command.CreateMedicineCommand;
import com.pharmacy_management.pharmacy_cmd_ms.command.RemoveMedicineCommand;
import com.pharmacy_management.pharmacy_core.event.MedicineRemovedEvent;
import com.pharmacy_management.pharmacy_core.event.NewMedicineCreatedEvent;
import com.pharmacy_management.pharmacy_core.model.Medicine;
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
public class MedicineAggregate {
    @AggregateIdentifier
    private String id;
    private Medicine medicine;
    private static final Logger log= LoggerFactory.getLogger(MedicineAggregate.class);

    @CommandHandler
    public MedicineAggregate(CreateMedicineCommand command) {
        log.info("MedicineAggregate(CreateMedicineCommand command) -> command received: " + command.toString());
        Medicine medicine=command.getMedicine();
        NewMedicineCreatedEvent event=new NewMedicineCreatedEvent();
        event.setId(command.getId());
        event.setMedicine(medicine);
        AggregateLifecycle.apply(event);
        log.info("MedicineAggregate(CreateMedicineCommand command) -> NewMedicineCreatedEvent published: " + event.toString());
    }
    @EventSourcingHandler
    public void on(NewMedicineCreatedEvent event) {
        this.id = event.getId();
        this.medicine = event.getMedicine();
    }
    @CommandHandler
    public void handle(RemoveMedicineCommand command) {
        log.info("handle(RemoveMedicineCommand command) -> command received: " + command.toString());
        MedicineRemovedEvent event = new MedicineRemovedEvent();
        event.setId(command.getId());
        AggregateLifecycle.apply(event);
        log.info("handle(RemoveMedicineCommand command) -> MedicineRemovedEvent published: " + event.toString());
    }
    @EventSourcingHandler
    public void on(MedicineRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
