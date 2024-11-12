package com.visit_management.visit_cmd_ms.service;

import com.visit_management.visit_cmd_ms.dto.UpdateVisitDto;
import com.visit_management.visit_cmd_ms.dto.VisitDto;
import com.visit_management.visit_cmd_ms.dto.VisitStatusDto;
import com.visit_management.visit_core.model.Visit;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface VisitService {
    String generateVisitId();
    String changeVisitStatus(String r,String receptionId,String visitStatus) throws ExecutionException, InterruptedException;
    void removeVisit(String receptionId, MessageHeaders headers) throws ExecutionException, InterruptedException;

}
