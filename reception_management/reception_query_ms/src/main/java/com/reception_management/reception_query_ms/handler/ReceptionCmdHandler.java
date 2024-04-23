package com.reception_management.reception_query_ms.handler;

import com.reception_management.reception_core.event.NewReceptionCreatedEvent;
import com.reception_management.reception_core.event.ReceptionRemovedEvent;
import com.reception_management.reception_core.event.ReceptionUpdatedEvent;

public interface ReceptionCmdHandler {
    void onNewReceptionCreatedEvent(NewReceptionCreatedEvent event) throws Exception;
    void onReceptionUpdatedEvent(ReceptionUpdatedEvent event);
    void onReceptionRemovedEvent(ReceptionRemovedEvent event);
}
