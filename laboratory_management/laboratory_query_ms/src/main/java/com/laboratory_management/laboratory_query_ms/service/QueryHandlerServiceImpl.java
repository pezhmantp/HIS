package com.laboratory_management.laboratory_query_ms.service;

import com.laboratory_management.laboratory_core.model.BloodTest;
import com.laboratory_management.laboratory_core.model.UrinalysisTest;
import com.laboratory_management.laboratory_core.response.TestResponse;
import com.laboratory_management.laboratory_core.response.TestResponses;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueryHandlerServiceImpl implements QueryHandlerService{
    @Override
    public TestResponses combineSearchTestsResults(List<BloodTest> bloodTestList, List<UrinalysisTest> urinalysisTests) {
        List<TestResponse> testResponses = new ArrayList<>();
        if(bloodTestList.size() > 0){
            bloodTestList.forEach(i -> testResponses.add(new TestResponse
                    (i.getTestId(),i.getVisitId(),"bloodTest",i.getStatus())));
        }
        if(urinalysisTests.size() > 0){
            urinalysisTests.forEach(j -> testResponses.add(new TestResponse
                    (j.getTestId(),j.getVisitId(),"urinalysisTest",j.getStatus())));
        }

        return new TestResponses(testResponses);
    }
}
