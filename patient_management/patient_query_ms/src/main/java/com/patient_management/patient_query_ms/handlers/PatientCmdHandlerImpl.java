package com.patient_management.patient_query_ms.handlers;

import com.patient_management.patient_core.event.NewPatientCreatedEvent;
import com.patient_management.patient_core.event.PatientRemovedEvent;
import com.patient_management.patient_core.event.PatientUpdatedEvent;
import com.patient_management.patient_core.repository.PatientRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("patient-group")
public class PatientCmdHandlerImpl implements PatientCmdHandler {

    private static final Logger log= LoggerFactory.getLogger(PatientCmdHandlerImpl.class);
    private final PatientRepository patientRepository;

    @Autowired
    public PatientCmdHandlerImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @EventHandler
    @Override
    public void onNewPatientCreatedEvent(NewPatientCreatedEvent event) {
        log.info("onNewPatientCreatedEvent() -> received patient: " + event.getPatient().toString());
        try {
            patientRepository.save(event.getPatient());
            log.info("onNewPatientCreatedEvent() -> patient saved: " + event.getPatient().toString());
        }
        catch (Exception e){
            log.error("Error at onNewPatientCreatedEvent() : " + e.getMessage());
        }

    }
    @EventHandler
    @Override
    public void onPatientUpdatedEvent(PatientUpdatedEvent event) {
        log.info("onPatientUpdatedEvent() -> received patient: " + event.getPatient().toString());
        try{
            patientRepository.save(event.getPatient());
            log.info("onPatientUpdatedEvent() -> patient saved: " + event.getPatient().toString());
        }
        catch (Exception e)
        {
            log.error("Error at onPatientUpdatedEvent() : " + e.getMessage());
        }

    }
    @EventHandler
    @Override
    public void onPatientRemovedEvent(PatientRemovedEvent event) {
        log.info("onPatientRemovedEvent() -> received patientId: " + event.getId().toString());
        try{
            patientRepository.deleteById(event.getId());
            log.info("onPatientRemovedEvent() -> patient deleted: " + event.getId().toString());
        }
        catch (Exception e){
            log.error("Error at onPatientRemovedEvent() : " + e.getMessage());
    }
    }

    @ExceptionHandler
    public void handler(Exception exception) throws Exception{
        throw exception;
    }
}
