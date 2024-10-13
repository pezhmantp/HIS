package com.laboratory_management.laboratory_query_ms.handler;

import com.laboratory_management.laboratory_core.model.BloodTest;
import com.laboratory_management.laboratory_core.model.UrinalysisTest;
import com.laboratory_management.laboratory_core.query.FindAllOpenTestsQuery;
import com.laboratory_management.laboratory_core.query.FindTestByTestIdQuery;
import com.laboratory_management.laboratory_core.query.FindTestsByVisitIdQuery;
import com.laboratory_management.laboratory_core.repository.BloodTestRepository;
import com.laboratory_management.laboratory_core.repository.UrinalysisTestRepository;
import com.laboratory_management.laboratory_core.response.CompleteBloodTestResponse;
import com.laboratory_management.laboratory_core.response.CompleteTestResponse;
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

    @QueryHandler
    @Override
    public TestResponses findTestsByVisitId(FindTestsByVisitIdQuery query) {
        List<UrinalysisTest> urinalysisTests = urinalysisTestRepository.findAllByVisitId(query.getVisitId());
        List<BloodTest> bloodTests = bloodTestRepository.findAllByVisitId(query.getVisitId());
        return queryHandlerService.combineSearchTestsResults(bloodTests,urinalysisTests);

    }

    @QueryHandler
    @Override
    public TestResponses findAllOpenTests(FindAllOpenTestsQuery query) {
        List<UrinalysisTest> urinalysisTests = urinalysisTestRepository.findAllOpenTests();
        List<BloodTest> bloodTests = bloodTestRepository.findAllOpenTests();
        return queryHandlerService.combineSearchTestsResults(bloodTests,urinalysisTests);
    }

    @QueryHandler
    @Override
    public CompleteTestResponse findTestByTestId(FindTestByTestIdQuery query) {

        switch (query.getTestType())
        {
            case "bloodTest" : return queryHandlerService.returnCompleteBloodTestResponse(bloodTestRepository.findTestByTestId(query.getTestId()),query.getTestType());
            case "urinalysisTest" : return queryHandlerService.returnCompleteUrinalysisTestResponse(urinalysisTestRepository.findTestByTestId(query.getTestId()),query.getTestType());
        }

        return new CompleteTestResponse(null,null,null);
    }

}
