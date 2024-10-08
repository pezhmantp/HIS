package com.laboratory_management.laboratory_query_ms.handler;

import com.laboratory_management.laboratory_core.event.BloodTestRemovedEvent;
import com.laboratory_management.laboratory_core.event.BloodTestUpdatedEvent;
import com.laboratory_management.laboratory_core.event.NewBloodTestCreatedEvent;

public interface BloodTestCmdHandler {
    void onNewBloodTestCreatedEvent(NewBloodTestCreatedEvent event);
    void onBloodTestUpdatedEvent(BloodTestUpdatedEvent event);
    void onBloodTestRemovedEvent(BloodTestRemovedEvent event);
}
