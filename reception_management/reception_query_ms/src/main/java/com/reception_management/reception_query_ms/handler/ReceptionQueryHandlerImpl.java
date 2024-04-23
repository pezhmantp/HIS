package com.reception_management.reception_query_ms.handler;

import com.reception_management.reception_core.model.Reception;
import com.reception_management.reception_core.queries.FindReceptionByReceptionIdQuery;
import com.reception_management.reception_core.repository.ReceptionRepository;
import com.reception_management.reception_core.responeObj.ReceptionResponseFromQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReceptionQueryHandlerImpl implements ReceptionQueryHandler {
    private final ReceptionRepository receptionRepository;
    private static final Logger log= LoggerFactory.getLogger(ReceptionQueryHandlerImpl.class);
    private DozerBeanMapper mapper;

    public ReceptionQueryHandlerImpl(ReceptionRepository receptionRepository) {
        this.receptionRepository = receptionRepository;
    }
    @QueryHandler
    @Override
    public ReceptionResponseFromQuery findReceptionByReceptionId(FindReceptionByReceptionIdQuery query) {
        log.info("findReceptionByReceptionId() -> receptionId received: "+query.getReceptionId());
        Reception tempReception = receptionRepository.findByReceptionId(query.getReceptionId());

        if (tempReception != null) {
            log.info("findReceptionByReceptionId() -> reception found: " + tempReception);
            mapper = new DozerBeanMapper();
            Reception reception = mapper.map(tempReception, Reception.class);
            System.out.println(reception.getReceptionId() + " " + reception.getPatientId());
            ReceptionResponseFromQuery receptionResponse = new ReceptionResponseFromQuery(reception,"reception found");
            return receptionResponse;
        }
        System.out.println("reception is null in handler");
        return new ReceptionResponseFromQuery(null,"reception not found");
    }

}
