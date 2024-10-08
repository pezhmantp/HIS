package com.laboratory_management.laboratory_query_ms.handler;

import com.laboratory_management.laboratory_core.event.BloodTestRemovedEvent;
import com.laboratory_management.laboratory_core.event.BloodTestUpdatedEvent;
import com.laboratory_management.laboratory_core.event.NewBloodTestCreatedEvent;
import com.laboratory_management.laboratory_core.repository.BloodTestRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("bloodTest-group")
public class BloodTestCmdHandlerImpl implements BloodTestCmdHandler {
    private final BloodTestRepository bloodTestRepository;
    private static final Logger log= LoggerFactory.getLogger(BloodTestCmdHandlerImpl.class);
    @Autowired
    public BloodTestCmdHandlerImpl(BloodTestRepository bloodTestRepository) {
        this.bloodTestRepository = bloodTestRepository;
    }
    @EventHandler
    @Override
    public void onNewBloodTestCreatedEvent(NewBloodTestCreatedEvent event) {
        try {
            log.info("onNewBloodTestCreatedEvent() -> received event: " + event.toString());
            bloodTestRepository.save(event.getBloodTest());
            log.info("onNewBloodTestCreatedEvent() -> bloodTest saved: " + event.getBloodTest().toString());
        }
        catch (Exception e){
            log.error("Error at onNewBloodTestCreatedEvent(): " + e.getMessage());
        }
    }
    @EventHandler
    @Override
    public void onBloodTestUpdatedEvent(BloodTestUpdatedEvent event) {
        try {
            log.info("onBloodTestUpdatedEvent() -> received event: " + event.toString());
            bloodTestRepository.save(event.getBloodTest());
            log.info("onBloodTestUpdatedEvent() -> bloodTest saved: " + event.getBloodTest().toString());
        }
        catch (Exception e){
            log.error("Error at onBloodTestUpdatedEvent(): " + e.getMessage());
        }
    }
    @EventHandler
    @Override
    public void onBloodTestRemovedEvent(BloodTestRemovedEvent event) {
        try{
            log.info("on(ReceptionRemovedEvent event) -> received event: " + event.getId());
            bloodTestRepository.deleteById(event.getId());
        }
        catch (Exception e){
            log.error("Error at on(ReceptionRemovedEvent event): " + e.getMessage());
        }
    }
    @ExceptionHandler
    public void handler(Exception exception) throws Exception{
        throw exception;
    }
}
