package com.pharmacy_management.pharmacy_query_ms.handler;

import com.pharmacy_management.pharmacy_core.event.MedicineRemovedEvent;
import com.pharmacy_management.pharmacy_core.event.NewMedicineCreatedEvent;
import com.pharmacy_management.pharmacy_core.repository.MedicineRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("medicine-group")
public class MedicineCommandHandlerImpl implements MedicineCommandHandler{
    private static final Logger log= LoggerFactory.getLogger(MedicineCommandHandlerImpl.class);
    @Autowired
    private MedicineRepository medicineRepository;

    @EventHandler
    @Override
    public void onNewMedicineCreatedEvent(NewMedicineCreatedEvent event) {
        System.out.println("@@@@@@@@@@@@@@@@@@@ " + event.getMedicine());
        try {
            medicineRepository.save(event.getMedicine());
        }
        catch (Exception e)
        {
            log.error("Error at onNewMedicineCreatedEvent() " + e.getMessage());
        }
    }

    @EventHandler
    @Override
    public void onMedicineRemovedEvent(MedicineRemovedEvent event) {
        System.out.println("VisitRemovedEvent implemented");
        try {
            medicineRepository.deleteById(event.getId());
        }
        catch (Exception e)
        {
            log.error("Error at onMedicineRemovedEvent() " + e.getMessage());
        }

    }
}
