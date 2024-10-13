package com.laboratory_management.laboratory_query_ms.service;

import com.laboratory_management.laboratory_core.model.BloodTest;
import com.laboratory_management.laboratory_core.model.UrinalysisTest;
import com.laboratory_management.laboratory_core.response.CompleteBloodTestResponse;
import com.laboratory_management.laboratory_core.response.CompleteUrinalysisTestResponse;
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

    @Override
    public CompleteBloodTestResponse mapBloodTestToCompleteBloodTestResponse(BloodTest bloodTest) {
        CompleteBloodTestResponse completeBloodTestResponse = new CompleteBloodTestResponse();
        completeBloodTestResponse.setTestId(bloodTest.getTestId());
        completeBloodTestResponse.setType("bloodTest");
        completeBloodTestResponse.setMcv(bloodTest.getMcv());
        completeBloodTestResponse.setMch(bloodTest.getMch());
        completeBloodTestResponse.setLymphocytes(bloodTest.getLymphocytes());
        completeBloodTestResponse.setHemoglobin(bloodTest.getHemoglobin());
        completeBloodTestResponse.setEosinophiles(bloodTest.getEosinophiles());
        completeBloodTestResponse.setMonocytes(bloodTest.getMonocytes());
        completeBloodTestResponse.setNeutrophils(bloodTest.getNeutrophils());
        completeBloodTestResponse.setPcv(bloodTest.getPcv());
        completeBloodTestResponse.setRbc(bloodTest.getRbc());
        completeBloodTestResponse.setWbc(bloodTest.getWbc());
        return completeBloodTestResponse;
    }
    @Override
    public CompleteUrinalysisTestResponse mapUrinalysisTestToCompleteUrinalysisTestResponse(UrinalysisTest urinalysisTest) {
        CompleteUrinalysisTestResponse completeUrinalysisTestResponse = new CompleteUrinalysisTestResponse();
        completeUrinalysisTestResponse.setBlood(urinalysisTest.getBlood());
        completeUrinalysisTestResponse.setType("urinalysisTest");
        completeUrinalysisTestResponse.setTestId(urinalysisTest.getTestId());
        completeUrinalysisTestResponse.setPh(urinalysisTest.getPh());
        completeUrinalysisTestResponse.setBilirubin(urinalysisTest.getBilirubin());
        completeUrinalysisTestResponse.setAppearance(urinalysisTest.getAppearance());
        completeUrinalysisTestResponse.setGlucose(urinalysisTest.getGlucose());
        completeUrinalysisTestResponse.setKetones(urinalysisTest.getKetones());
        completeUrinalysisTestResponse.setLeukocyteEsterase(urinalysisTest.getLeukocyteEsterase());
        completeUrinalysisTestResponse.setNitrite(urinalysisTest.getNitrite());
        completeUrinalysisTestResponse.setProtein(urinalysisTest.getProtein());
        completeUrinalysisTestResponse.setSpecificGravity(urinalysisTest.getSpecificGravity());
        completeUrinalysisTestResponse.setUrobilinogen(urinalysisTest.getUrobilinogen());
        completeUrinalysisTestResponse.setColor(urinalysisTest.getColor());
        return completeUrinalysisTestResponse;
    }
    @Override
    public CompleteBloodTestResponse returnCompleteBloodTestResponse(BloodTest bloodTest, String testType) {

        CompleteBloodTestResponse completeBloodTestResponse = this.mapBloodTestToCompleteBloodTestResponse(bloodTest);
        completeBloodTestResponse.setType(testType);
        return completeBloodTestResponse;
    }

    @Override
    public CompleteUrinalysisTestResponse returnCompleteUrinalysisTestResponse(UrinalysisTest urinalysisTest, String testType) {
        CompleteUrinalysisTestResponse completeUrinalysisTestResponse = this.mapUrinalysisTestToCompleteUrinalysisTestResponse(urinalysisTest);
        completeUrinalysisTestResponse.setType(testType);
        return completeUrinalysisTestResponse;
    }

}
