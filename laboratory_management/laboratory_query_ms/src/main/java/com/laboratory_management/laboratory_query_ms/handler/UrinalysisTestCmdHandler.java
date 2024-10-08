package com.laboratory_management.laboratory_query_ms.handler;

import com.laboratory_management.laboratory_core.event.NewUrinalysisTestCreatedEvent;
import com.laboratory_management.laboratory_core.event.UrinalysisTestRemovedEvent;
import com.laboratory_management.laboratory_core.event.UrinalysisTestUpdatedEvent;

public interface UrinalysisTestCmdHandler {
    void onNewUrinalysisTestCreatedEvent(NewUrinalysisTestCreatedEvent event) throws Exception;
    void onUrinalysisTestUpdatedEvent(UrinalysisTestUpdatedEvent event);
    void onUrinalysisTestRemovedEvent(UrinalysisTestRemovedEvent event);
}
