package com.pharmacy_management.pharmacy_query_ms.handler;

import com.pharmacy_management.pharmacy_core.event.MedicineRemovedEvent;
import com.pharmacy_management.pharmacy_core.event.NewMedicineRequestCreatedEvent;
import com.pharmacy_management.pharmacy_core.repository.MedicineRequestRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("medicineRequest-group")
public class MedicineRequestCommandHandlerImpl implements MedicineRequestCommandHandler {
    private static final Logger log= LoggerFactory.getLogger(MedicineRequestCommandHandlerImpl.class);
    @Autowired
    private MedicineRequestRepository medicineRequestRepository;

    @EventHandler
    @Override
    public void onNewMedicineRequestCreatedEvent(NewMedicineRequestCreatedEvent event) {
        try {
            medicineRequestRepository.save(event.getMedicineRequest());
        }
        catch (Exception e)
        {
            log.error("Error at onNewMedicineRequestCreatedEvent() " + e.getMessage());
        }
    }


}
