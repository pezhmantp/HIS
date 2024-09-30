package com.visit_management.visit_query_ms.handler;

import com.visit_management.visit_core.event.NewVisitCreatedEvent;
import com.visit_management.visit_core.event.VisitRemovedEvent;
import com.visit_management.visit_core.event.VisitUpdatedEvent;
import com.visit_management.visit_core.repository.VisitRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("visit-group")
public class VisitCommandHandlerImpl implements VisitCommandHandler{
    private static final Logger log= LoggerFactory.getLogger(VisitCommandHandlerImpl.class);
    @Autowired
    private VisitRepository visitRepository;

    @EventHandler
    @Override
    public void onNewVisitCreatedEvent(NewVisitCreatedEvent event) {
        try {
            visitRepository.save(event.getVisit());
        }
        catch (Exception e)
        {
            log.error("Error at onNewVisitCreatedEvent() " + e.getMessage());
        }

    }

    @EventHandler
    @Override
    public void onVisitUpdatedEvent(VisitUpdatedEvent event) {
        try {
            visitRepository.save(event.getVisit());
        }
        catch (Exception e)
        {
            log.error("Error at onVisitUpdatedEvent() " + e.getMessage());
        }
    }

    @EventHandler
    @Override
    public void onVisitRemovedEvent(VisitRemovedEvent event) {
        System.out.println("VisitRemovedEvent implemented");
        try {
            visitRepository.deleteByVisitId(event.getId());
        }
        catch (Exception e)
        {
            log.error("Error at onVisitRemovedEvent() " + e.getMessage());
        }

    }
}
