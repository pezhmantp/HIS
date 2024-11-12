package com.pharmacy_management.pharmacy_query_ms.handler;

import com.pharmacy_management.pharmacy_core.event.NewMedicineRequestCreatedEvent;

public interface MedicineRequestCommandHandler {
    void onNewMedicineRequestCreatedEvent(NewMedicineRequestCreatedEvent event);
}
