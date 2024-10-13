package com.laboratory_management.laboratory_query_ms.service;

import com.laboratory_management.laboratory_core.model.BloodTest;
import com.laboratory_management.laboratory_core.model.UrinalysisTest;
import com.laboratory_management.laboratory_core.response.*;

import java.util.List;

public interface QueryHandlerService {
    TestResponses combineSearchTestsResults(List<BloodTest> bloodTestList, List<UrinalysisTest> urinalysisTests);

    CompleteBloodTestResponse mapBloodTestToCompleteBloodTestResponse(BloodTest bloodTest);
    CompleteUrinalysisTestResponse mapUrinalysisTestToCompleteUrinalysisTestResponse(UrinalysisTest urinalysisTest);
    CompleteBloodTestResponse returnCompleteBloodTestResponse(BloodTest bloodTest, String testType);
    CompleteUrinalysisTestResponse returnCompleteUrinalysisTestResponse(UrinalysisTest urinalysisTest, String testType);
}