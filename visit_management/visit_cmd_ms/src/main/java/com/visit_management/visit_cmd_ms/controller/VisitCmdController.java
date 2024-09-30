package com.visit_management.visit_cmd_ms.controller;

import com.visit_management.visit_cmd_ms.command.CreateVisitCommand;
import com.visit_management.visit_cmd_ms.service.VisitService;
import com.visit_management.visit_core.model.Visit;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/visitCmd")
public class VisitCmdController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private VisitService visitService;
    private static final Logger log= LoggerFactory.getLogger(VisitCmdController.class);

//    @PreAuthorize("hasRole('doctor')")
    @PostMapping
    public CompletableFuture<String> addNewVisit(@RequestBody String receptionId)
    {

        Visit visit=new Visit();
        visit.setSeen(true);
        visit.setVisitId(visitService.generateVisitId());
        visit.setReceptionId(receptionId);
        CreateVisitCommand cmd = new CreateVisitCommand();
        cmd.setId(visit.getVisitId());
        cmd.setVisit(visit);
        log.info("add(): before sending CreateVisitCommand to CommandGateway. Reception Id:" + receptionId);

        return this.commandGateway.send(cmd).thenApply(r -> {
            try {
                return visitService.changeVisitStatus(r.toString(),receptionId,"in progress");
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
