package com.laboratory_management.laboratory_cmd_ms.service;

import com.laboratory_management.laboratory_cmd_ms.command.CreateUrinalysisTestCommand;
import com.laboratory_management.laboratory_cmd_ms.command.DeleteUrinalysisTestCommand;
import com.laboratory_management.laboratory_cmd_ms.command.UpdateUrinalysisTestCommand;
import com.laboratory_management.laboratory_core.dto.TestDto;
import com.laboratory_management.laboratory_core.dto.UrinalysisTestDto;
import com.laboratory_management.laboratory_core.model.UrinalysisTest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class UrinalysisTestRequest implements TestOperation{
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private LabCmdService labCmdService;

    @Override
    public CompletableFuture<Map<String,String>> requestNewTest(String visitId) {
        UrinalysisTest urinalysisTest=new UrinalysisTest();
        urinalysisTest.setVisitId(visitId);
        urinalysisTest.setStatus("in progress");
        String testId=labCmdService.generateTestId();
        urinalysisTest.setTestId(testId);

        CreateUrinalysisTestCommand cmd = new CreateUrinalysisTestCommand();
        cmd.setId(testId);
        cmd.setUrinalysisTest(urinalysisTest);
        return this.commandGateway.send(cmd).thenApply(r -> mapResult("testId",r.toString(),"visitId",visitId))
                .exceptionally(throwable -> mapError("error",throwable.getMessage()));
    }

    @Override
    public CompletableFuture<Map<String, String>> removeTest(String testId) {
        DeleteUrinalysisTestCommand command=new DeleteUrinalysisTestCommand();
        command.setId(testId);
        return this.commandGateway.send(command).thenApply(r -> mapResult("testId",r.toString(),"visitId",null))
                .exceptionally(throwable -> mapError("error",throwable.getMessage()));
    }
    @Override
    public CompletableFuture<Map<String, String>> updateTest(TestDto testDto) {
        UrinalysisTestDto urinalysisTestDto=(UrinalysisTestDto) testDto;
        UrinalysisTest urinalysisTest= labCmdService.mapUrinalysisTestDtoToUrinalysisTest(urinalysisTestDto);
        UpdateUrinalysisTestCommand cmd = new UpdateUrinalysisTestCommand();
        cmd.setId(urinalysisTestDto.getTestId());
        cmd.setUrinalysisTest(urinalysisTest);
        return this.commandGateway.send(cmd).thenApply(r -> mapResult("testId",urinalysisTestDto.getTestId(),"visitId",testDto.getVisitId()))
                .exceptionally(throwable -> mapError("error",throwable.getMessage()));
    }
    private Map<String,String> mapResult(String testIdKey,String testIdValue,String visitIdKey, String visitIdValue){
        Map<String,String> result=new HashMap<>();
        result.put(testIdKey,testIdValue);
        result.put(visitIdKey,visitIdValue);
        return result;
    }

    private Map<String,String> mapError(String error,String msg){
        Map<String,String> result=new HashMap<>();
        result.put(error,msg);
        return result;
    }

}
