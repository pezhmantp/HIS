package com.reception_management.reception_query_ms.handler;

import com.reception_management.reception_core.event.NewReceptionCreatedEvent;
import com.reception_management.reception_core.event.ReceptionRemovedEvent;
import com.reception_management.reception_core.event.ReceptionUpdatedEvent;
import com.reception_management.reception_core.repository.ReceptionRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("reception-group")
public class ReceptionCmdHandlerImpl implements ReceptionCmdHandler {
    private final ReceptionRepository receptionRepository;
    private static final Logger log= LoggerFactory.getLogger(ReceptionCmdHandlerImpl.class);
    @Autowired
    public ReceptionCmdHandlerImpl(ReceptionRepository receptionRepository) {
        this.receptionRepository = receptionRepository;
    }
    @EventHandler
    @Override
    public void onNewReceptionCreatedEvent(NewReceptionCreatedEvent event) {
        try {
            log.info("on(NewReceptionCreatedEvent event) -> received event: " + event.toString());
            receptionRepository.save(event.getReception());
            log.info("on(NewReceptionCreatedEvent event) -> reception saved: " + event.getReception().toString());
        }
        catch (Exception e){
            log.error("Error at on(NewReceptionCreatedEvent event): " + e.getMessage());
        }
    }
    @EventHandler
    @Override
    public void onReceptionUpdatedEvent(ReceptionUpdatedEvent event) {
        try {
            log.info("on(ReceptionUpdatedEvent event) -> received event: " + event.toString());
            receptionRepository.save(event.getReception());
            log.info("on(ReceptionUpdatedEvent event) -> reception saved: " + event.getReception().toString());
        }
        catch (Exception e){
            log.error("Error at on(ReceptionUpdatedEvent event): " + e.getMessage());
        }

    }
    @EventHandler
    @Override
    public void onReceptionRemovedEvent(ReceptionRemovedEvent event) {
        try{
            log.info("on(ReceptionRemovedEvent event) -> received event: " + event.getId());
            receptionRepository.deleteById(event.getId());
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
