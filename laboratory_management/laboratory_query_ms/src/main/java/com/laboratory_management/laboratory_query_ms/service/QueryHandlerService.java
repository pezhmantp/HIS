package com.laboratory_management.laboratory_query_ms.service;

import com.laboratory_management.laboratory_core.model.BloodTest;
import com.laboratory_management.laboratory_core.model.UrinalysisTest;
import com.laboratory_management.laboratory_core.response.TestResponse;
import com.laboratory_management.laboratory_core.response.TestResponses;

import java.util.List;

public interface QueryHandlerService {
    TestResponses combineSearchTestsResults(List<BloodTest> bloodTestList, List<UrinalysisTest> urinalysisTests);
}
