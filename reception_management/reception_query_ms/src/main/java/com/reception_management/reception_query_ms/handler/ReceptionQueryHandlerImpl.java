package com.reception_management.reception_query_ms.handler;

import com.reception_management.reception_core.model.Reception;
import com.reception_management.reception_core.queries.FindReceptionByReceptionIdQuery;
import com.reception_management.reception_core.queries.FindReceptionsByDoctorIdQuery;
import com.reception_management.reception_core.repository.ReceptionRepository;
import com.reception_management.reception_core.responeObj.ReceptionResponse;
import com.reception_management.reception_core.responeObj.ReceptionResponseFromQuery;
import com.reception_management.reception_core.responeObj.ReceptionsResponse;
import org.axonframework.queryhandling.QueryHandler;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public ReceptionResponse findReceptionByReceptionId(FindReceptionByReceptionIdQuery query) {
        log.info("findReceptionByReceptionId() -> receptionId received: "+query.getReceptionId());
        Reception tempReception = receptionRepository.findByReceptionId(query.getReceptionId());

        if (tempReception != null) {
            log.info("findReceptionByReceptionId() -> reception found: " + tempReception);
            mapper = new DozerBeanMapper();
            Reception reception = mapper.map(tempReception, Reception.class);
            System.out.println(reception.getReceptionId() + " " + reception.getPatientId());
            ReceptionResponse receptionResponse = new ReceptionResponse(tempReception,"reception found");
            return receptionResponse;
        }
        System.out.println("reception is null in handler");
        return new ReceptionResponse(null,"reception not found");
    }
    @QueryHandler
    @Override
    public ReceptionsResponse findReceptionsByDoctorId(FindReceptionsByDoctorIdQuery query) {
        log.info("findReceptionByReceptionId() -> receptionId received: "+query.getDoctorId());
        List<Reception> receptions = receptionRepository.findByDoctorId(query.getDoctorId());
        List<Reception> receptionsList = new ArrayList<>();

        receptionsList.forEach((p) -> {
            mapper = new DozerBeanMapper();
            Reception reception = mapper.map(p, Reception.class);
            receptionsList.add(reception);
        });

//        log.info("founddddddddddddddddd: "+receptionsList);
        ReceptionsResponse receptionsResponse=new ReceptionsResponse();
        receptionsResponse.setReceptions(receptions);
        if(receptions.size() > 0)
        {
            receptionsResponse.setMessage("Receptions found!");
        }
        else {
            receptionsResponse.setMessage("No reception found!");
        }

        return receptionsResponse;
    }
}
