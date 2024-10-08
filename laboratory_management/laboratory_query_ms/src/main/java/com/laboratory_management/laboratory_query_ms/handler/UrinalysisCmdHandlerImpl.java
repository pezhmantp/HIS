package com.laboratory_management.laboratory_query_ms.handler;

import com.laboratory_management.laboratory_core.event.NewUrinalysisTestCreatedEvent;
import com.laboratory_management.laboratory_core.event.UrinalysisTestRemovedEvent;
import com.laboratory_management.laboratory_core.event.UrinalysisTestUpdatedEvent;
import com.laboratory_management.laboratory_core.repository.UrinalysisTestRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("urinalysisTest-group")
public class UrinalysisCmdHandlerImpl implements UrinalysisTestCmdHandler{
    private final UrinalysisTestRepository urinalysisTestRepository;
    private static final Logger log= LoggerFactory.getLogger(UrinalysisCmdHandlerImpl.class);
    @Autowired
    public UrinalysisCmdHandlerImpl(UrinalysisTestRepository urinalysisTestRepository) {
        this.urinalysisTestRepository = urinalysisTestRepository;
    }
    @EventHandler
    @Override
    public void onNewUrinalysisTestCreatedEvent(NewUrinalysisTestCreatedEvent event) throws Exception {
        try {
            log.info("onNewUrinalysisTestCreatedEvent() -> received event: " + event.toString());
            urinalysisTestRepository.save(event.getUrinalysisTest());
            log.info("onNewUrinalysisTestCreatedEvent() -> urinalysisTest saved: " + event.getUrinalysisTest().toString());
        }
        catch (Exception e){
            log.error("Error at onNewUrinalysisTestCreatedEvent(): " + e.getMessage());
        }
    }
    @EventHandler
    @Override
    public void onUrinalysisTestUpdatedEvent(UrinalysisTestUpdatedEvent event) {
        try {
            log.info("onUrinalysisTestUpdatedEvent() -> received event: " + event.toString());
            urinalysisTestRepository.save(event.getUrinalysisTest());
            log.info("UrinalysisTestUpdatedEvent() -> urinalysisTest saved: " + event.getUrinalysisTest().toString());
        }
        catch (Exception e){
            log.error("Error at onUrinalysisTestUpdatedEvent(): " + e.getMessage());
        }
    }
    @EventHandler
    @Override
    public void onUrinalysisTestRemovedEvent(UrinalysisTestRemovedEvent event) {
        try {
            log.info("onUrinalysisTestRemovedEvent() -> received event: " + event.toString());
            urinalysisTestRepository.deleteById(event.getId());

        } catch (Exception e) {
            log.error("Error at onUrinalysisTestRemovedEvent(): " + e.getMessage());
        }
    }
    @ExceptionHandler
    public void handler(Exception exception) throws Exception{
        throw exception;
    }
}
