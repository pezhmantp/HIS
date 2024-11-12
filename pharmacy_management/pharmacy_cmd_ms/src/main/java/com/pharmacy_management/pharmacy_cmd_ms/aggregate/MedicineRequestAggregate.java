package com.pharmacy_management.pharmacy_cmd_ms.aggregate;

import com.pharmacy_management.pharmacy_cmd_ms.command.CreateMedicineRequestCommand;
import com.pharmacy_management.pharmacy_core.event.NewMedicineRequestCreatedEvent;
import com.pharmacy_management.pharmacy_core.model.MedicineRequest;
import com.pharmacy_management.pharmacy_core.model.Prescription;
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
public class MedicineRequestAggregate {
    @AggregateIdentifier
    private String id;
    private MedicineRequest medicineRequest;
    private static final Logger log= LoggerFactory.getLogger(MedicineRequestAggregate.class);

    @CommandHandler
    public MedicineRequestAggregate(CreateMedicineRequestCommand command) {
        log.info("MedicineRequestAggregate(CreateMedicineRequestCommand command) -> command received: " + command.toString());
        MedicineRequest medicineRequest=command.getMedicineRequest();
        NewMedicineRequestCreatedEvent event=new NewMedicineRequestCreatedEvent();
        event.setId(command.getId());
        event.setMedicineRequest(medicineRequest);
        AggregateLifecycle.apply(event);
        log.info("MedicineRequestAggregate(CreateMedicineRequestCommand command) -> NewMedicineRequestCreatedEvent published: " + event.toString());
    }
    @EventSourcingHandler
    public void on(NewMedicineRequestCreatedEvent event) {
        this.id = event.getId();
        this.medicineRequest = event.getMedicineRequest();
    }

}
