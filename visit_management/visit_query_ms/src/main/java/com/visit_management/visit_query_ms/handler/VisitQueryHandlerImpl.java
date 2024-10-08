package com.visit_management.visit_query_ms.handler;

import com.visit_management.visit_core.event.NewVisitCreatedEvent;
import com.visit_management.visit_core.model.Visit;
import com.visit_management.visit_core.query.FindVisitByReceptionIdQuery;
import com.visit_management.visit_core.repository.VisitRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VisitQueryHandlerImpl implements VisitQueryHandler{

    @Autowired
    private VisitRepository visitRepository;

    @QueryHandler
    @Override
    public String findVisitByReceptionId(FindVisitByReceptionIdQuery query) {
        Visit visit = visitRepository.findVisitByReceptionId(query.getReceptionId());
        if(visit == null)
        {
            return null;
        }
        return visit.getVisitId();
    }
}
