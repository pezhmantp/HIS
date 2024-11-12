package com.pharmacy_management.pharmacy_cmd_ms.controller;

import com.pharmacy_management.pharmacy_cmd_ms.command.CreateMedicineRequestCommand;
import com.pharmacy_management.pharmacy_cmd_ms.dto.MedicineRequestDto;
import com.pharmacy_management.pharmacy_cmd_ms.service.PharmacyService;
import com.pharmacy_management.pharmacy_core.model.MedicineRequest;
import com.pharmacy_management.pharmacy_core.repository.MedicineRequestRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/medicineRequestCmd")
public class MedicineRequestCmdController {
    @Autowired
    private PharmacyService pharmacyService;
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private MedicineRequestRepository medicationRequestRepository;
    @PostMapping
    public CompletableFuture<String> addNewMedicineRequest(@RequestBody MedicineRequestDto medicationRequestDto){
        System.out.println(">>>>>> "+medicationRequestDto.toString());
         MedicineRequest medicineRequest= pharmacyService.mapMedicineRequestDtoToMedicineRequest(medicationRequestDto);
        CreateMedicineRequestCommand command=new CreateMedicineRequestCommand();
        command.setMedicineRequest(medicineRequest);
        command.setId(medicineRequest.getMedicineRequestId());
        CompletableFuture<String> result= commandGateway.send(command).thenApply(r -> r.toString());
        return result;
    }
}
