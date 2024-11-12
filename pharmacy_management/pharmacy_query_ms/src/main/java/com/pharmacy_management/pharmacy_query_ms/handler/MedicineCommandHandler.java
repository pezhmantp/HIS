package com.pharmacy_management.pharmacy_query_ms.handler;

import com.pharmacy_management.pharmacy_core.event.MedicineRemovedEvent;
import com.pharmacy_management.pharmacy_core.event.NewMedicineCreatedEvent;

public interface MedicineCommandHandler {
    void onNewMedicineCreatedEvent(NewMedicineCreatedEvent event);
    void onMedicineRemovedEvent(MedicineRemovedEvent event);
}
