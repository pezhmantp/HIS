package com.laboratory_management.laboratory_cmd_ms.service;

import com.laboratory_management.laboratory_core.dto.TestDto;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface TestOperation {
    CompletableFuture<Map<String,String>> requestNewTest(String visitId);
    CompletableFuture<Map<String,String>> removeTest(String testId);
    CompletableFuture<Map<String,String>> updateTest(TestDto testDto);
}
