package com.laboratory_management.laboratory_cmd_ms.controller;

import com.laboratory_management.laboratory_cmd_ms.service.TestOperation;
import com.laboratory_management.laboratory_cmd_ms.service.TestRequestFactory;
import com.laboratory_management.laboratory_core.dto.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/laboratoryCmd/test")
public class LabCmdController {
    @Autowired
    private TestRequestFactory testRequestFactory;

    @PostMapping
    public CompletableFuture<Map<String,String>> addNewTest(@RequestParam String visitId, @RequestParam String testType){

        TestOperation testOperation=testRequestFactory.getTestRequest(testType);
        return testOperation.requestNewTest(visitId);
    }

    @PutMapping
    public CompletableFuture<Map<String, String>> updateTest(@RequestBody TestDto testDto){
        TestOperation testOperation= testRequestFactory.getTestRequest(testDto.getClass().getSimpleName());
        return testOperation.updateTest(testDto);
    }
    @DeleteMapping
    public CompletableFuture<Map<String,String>> removeTest(@RequestParam String testId, @RequestParam String testType){

        TestOperation testOperation=testRequestFactory.getTestRequest(testType);
        return testOperation.removeTest(testId);
    }
}
