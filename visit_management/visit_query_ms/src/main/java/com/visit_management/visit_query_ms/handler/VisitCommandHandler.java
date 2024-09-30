package com.visit_management.visit_query_ms.handler;

import com.visit_management.visit_core.event.NewVisitCreatedEvent;
import com.visit_management.visit_core.event.VisitRemovedEvent;
import com.visit_management.visit_core.event.VisitUpdatedEvent;

public interface VisitCommandHandler {
    void onNewVisitCreatedEvent(NewVisitCreatedEvent event);
    void onVisitUpdatedEvent(VisitUpdatedEvent event);
    void onVisitRemovedEvent(VisitRemovedEvent event);
}
