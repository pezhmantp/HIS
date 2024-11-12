package com.pharmacy_management.pharmacy_cmd_ms.controller;

import com.pharmacy_management.pharmacy_cmd_ms.command.CreateMedicineCommand;
import com.pharmacy_management.pharmacy_cmd_ms.dto.MedicineDto;
import com.pharmacy_management.pharmacy_cmd_ms.dto.MedicineRequestDto;
import com.pharmacy_management.pharmacy_cmd_ms.service.PharmacyService;
import com.pharmacy_management.pharmacy_core.model.Medicine;
import com.pharmacy_management.pharmacy_core.model.MedicineRequest;
import com.pharmacy_management.pharmacy_core.repository.MedicineRepository;
import com.pharmacy_management.pharmacy_core.repository.MedicineRequestRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/medicineCmd")
public class MedicineCmdController {
    @Autowired
    private PharmacyService pharmacyService;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private CommandGateway commandGateway;
    private static final Logger log= LoggerFactory.getLogger(MedicineCmdController.class);
    @PostMapping
    public CompletableFuture<String> addNewMedicine(@RequestBody String medicineName){

        log.info("addNewMedicine(): medicineDto received from client " + medicineName);

            CreateMedicineCommand cmd = new CreateMedicineCommand();
            Medicine medicine=pharmacyService.mapMedicineDtoToMedicine(medicineName);
            cmd.setId(medicine.getMedicineId());
            cmd.setMedicine(medicine);
            return this.commandGateway.send(cmd).thenApply(r -> r.toString()).exceptionally(t -> t.getMessage());

    }
}
