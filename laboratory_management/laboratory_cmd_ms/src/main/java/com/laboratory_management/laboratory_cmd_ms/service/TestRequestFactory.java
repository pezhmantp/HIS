package com.laboratory_management.laboratory_cmd_ms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestRequestFactory {
    @Autowired
    private BloodTestRequest bloodTestRequest;
    @Autowired
    private UrinalysisTestRequest urinalysisTestRequest;
    public TestOperation getTestRequest(String testType){
        switch (testType){
            case "bloodTest" : return bloodTestRequest;
            case "urinalysisTest" : return urinalysisTestRequest;
        }
        return null;
    }
}
