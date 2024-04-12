package com.patient_management.patient_query_ms.handlers;

import com.patient_management.patient_core.event.NewPatientCreatedEvent;
import com.patient_management.patient_core.event.PatientRemovedEvent;
import com.patient_management.patient_core.event.PatientUpdatedEvent;

public interface PatientCmdHandler {
    void onNewPatientCreatedEvent(NewPatientCreatedEvent event) throws Exception;
    void onPatientUpdatedEvent(PatientUpdatedEvent event);
    void onPatientRemovedEvent(PatientRemovedEvent event);
}
