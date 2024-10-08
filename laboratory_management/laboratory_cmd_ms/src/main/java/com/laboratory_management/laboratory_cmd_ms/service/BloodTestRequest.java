package com.laboratory_management.laboratory_cmd_ms.service;

import com.laboratory_management.laboratory_cmd_ms.command.*;
import com.laboratory_management.laboratory_core.dto.BloodTestDto;
import com.laboratory_management.laboratory_core.dto.TestDto;
import com.laboratory_management.laboratory_core.dto.UrinalysisTestDto;
import com.laboratory_management.laboratory_core.model.BloodTest;
import com.laboratory_management.laboratory_core.model.UrinalysisTest;
import com.laboratory_management.laboratory_core.repository.BloodTestRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class BloodTestRequest implements TestOperation {

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private LabCmdService labCmdService;

    @Override
    public CompletableFuture<Map<String, String>> requestNewTest(String visitId) {
        BloodTest bloodTest=new BloodTest();
        bloodTest.setVisitId(visitId);
        bloodTest.setStatus("in progress");
        String testId=labCmdService.generateTestId();
        bloodTest.setTestId(testId);
        CreateBloodTestCommand cmd = new CreateBloodTestCommand();
        cmd.setId(testId);
        cmd.setBloodTest(bloodTest);

        return this.commandGateway.send(cmd).thenApply(r -> mapResult("testId",r.toString(),"visitId",visitId))
                .exceptionally(throwable -> mapError("error","Error occurred"));
    }

    @Override
    public CompletableFuture<Map<String, String>> removeTest(String testId) {
        DeleteBloodTestCommand command=new DeleteBloodTestCommand();
        command.setId(testId);
        return this.commandGateway.send(command).thenApply(r -> mapResult("testId",r.toString(),"visitId",null))
                .exceptionally(throwable -> mapError("error","Error occurred"));
    }

    @Override
    public CompletableFuture<Map<String, String>> updateTest(TestDto testDto) {
        BloodTestDto bloodTestDto=(BloodTestDto) testDto;
        BloodTest bloodTest= labCmdService.mapBloodTestDtoToBloodTest(bloodTestDto);
        UpdateBloodTestCommand cmd = new UpdateBloodTestCommand();
        cmd.setId(bloodTestDto.getTestId());
        cmd.setBloodTest(bloodTest);
        return this.commandGateway.send(cmd).thenApply(r -> mapResult("testId",r.toString(),"visitId",testDto.getVisitId()))
                .exceptionally(throwable -> mapError("error","Error occurred"));
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
