package com.laboratory_management.laboratory_query_ms.handler;

import com.laboratory_management.laboratory_core.model.BloodTest;
import com.laboratory_management.laboratory_core.model.UrinalysisTest;
import com.laboratory_management.laboratory_core.query.FindTestsByVisitIdQuery;
import com.laboratory_management.laboratory_core.repository.BloodTestRepository;
import com.laboratory_management.laboratory_core.repository.UrinalysisTestRepository;
import com.laboratory_management.laboratory_core.response.TestResponse;
import com.laboratory_management.laboratory_core.response.TestResponses;
import com.laboratory_management.laboratory_query_ms.service.QueryHandlerService;
import org.axonframework.queryhandling.QueryHandler;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
//import org.dozer.DozerBeanMapper;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestQueryHandlerImpl implements TestQueryHandler {
    @Autowired
    private QueryHandlerService queryHandlerService;
    private final BloodTestRepository bloodTestRepository;
    private final UrinalysisTestRepository urinalysisTestRepository;
    private static final Logger log= LoggerFactory.getLogger(TestQueryHandlerImpl.class);
    private DozerBeanMapper mapper;

    public TestQueryHandlerImpl(BloodTestRepository bloodTestRepository, UrinalysisTestRepository urinalysisTestRepository) {

        this.bloodTestRepository = bloodTestRepository;
        this.urinalysisTestRepository = urinalysisTestRepository;
    }
//    @QueryHandler
//    @Override
//    public ReceptionResponse findReceptionByReceptionId(FindReceptionByReceptionIdQuery query) {
//        log.info("findReceptionByReceptionId() -> receptionId received: "+query.getReceptionId());
//        Reception tempReception = receptionRepository.findByReceptionId(query.getReceptionId());
//
//        if (tempReception != null) {
//            log.info("findReceptionByReceptionId() -> reception found: " + tempReception);
//            mapper = new DozerBeanMapper();
//            Reception reception = mapper.map(tempReception, Reception.class);
//            System.out.println(reception.getReceptionId() + " " + reception.getPatientId());
//            ReceptionResponse receptionResponse = new ReceptionResponse(tempReception,"reception found");
//            return receptionResponse;
//        }
//        System.out.println("reception is null in handler");
//        return new ReceptionResponse(null,"reception not found");
//    }

    @QueryHandler
    @Override
    public TestResponses findTestsByVisitId(FindTestsByVisitIdQuery event) {
        List<UrinalysisTest> urinalysisTests = urinalysisTestRepository.findAllByVisitId(event.getVisitId());
        List<BloodTest> bloodTests = bloodTestRepository.findAllByVisitId(event.getVisitId());
        return queryHandlerService.combineSearchTestsResults(bloodTests,urinalysisTests);

    }
}
