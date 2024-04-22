package com.reception_management.reception_cmd_ms.controller;


import com.reception_management.reception_cmd_ms.command.CreateReceptionCmd;
import com.reception_management.reception_cmd_ms.command.RemoveReceptionCmd;
import com.reception_management.reception_cmd_ms.command.UpdateReceptionCmd;
import com.reception_management.reception_cmd_ms.dto.ReceptionDto;
import com.reception_management.reception_cmd_ms.dto.UpdateReceptionDto;
import com.reception_management.reception_cmd_ms.service.ReceptionService;
import com.reception_management.reception_core.model.Reception;
import com.reception_management.reception_core.responeObj.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping({"/receptionCmd"})
public class ReceptionCmdController {
    @Autowired
    private CommandGateway commandGateway;
    private static final Logger log = LoggerFactory.getLogger(ReceptionCmdController.class);
    @Autowired
    private ReceptionService receptionService;

    public ReceptionCmdController() {
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ReceptionDto receptionDto) {
        log.info("add(): receptionDto received from client " + receptionDto.toString());
        ReceptionResponse receptionResponse = new ReceptionResponse();

        try {
            Reception reception = this.receptionService.mapReceptionDtoToReception(receptionDto);
            CreateReceptionCmd cmd = new CreateReceptionCmd();
            cmd.setId(reception.getReceptionId());
            cmd.setReception(reception);
            log.info("add(): before sending CreateReceptionCmd to CommandGateway " + receptionDto.toString());
            this.commandGateway.sendAndWait(cmd);
            receptionResponse.setReceptionId(reception.getReceptionId());
            receptionResponse.setFetchedDoctor((FetchedDoctor)null);
            receptionResponse.setDepartment("??");
            receptionResponse.setFetchedPatient((FetchedPatient)null);
            receptionResponse.setMessage("New reception created");
            log.info("add(): New reception created: " + receptionResponse.toString());
            return new ResponseEntity(receptionResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error at add() : " + e.getMessage());
            return new ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UpdateReceptionDto updateReceptionDto) {
        UpdateReceptionCmd cmd = new UpdateReceptionCmd();
        ReceptionResponseFromQuery receptionResponseFromQuery = this.receptionService.getReception(updateReceptionDto.getReceptionId());
        if (receptionResponseFromQuery.getReception() == null) {
            return new ResponseEntity(receptionResponseFromQuery, HttpStatus.NOT_FOUND);
        } else {
            cmd.setId(receptionResponseFromQuery.getReception().getReceptionId());
            Reception toBeUpdatedReception = this.receptionService.mapUpdateReceptionDtoToReception(updateReceptionDto);
            toBeUpdatedReception.setReceptionId(updateReceptionDto.getReceptionId());
            cmd.setReception(toBeUpdatedReception);
            this.commandGateway.sendAndWait(cmd);
            ReceptionResponseFromQuery response = this.receptionService.getReception(updateReceptionDto.getReceptionId());
            return new ResponseEntity(response.getReception(), HttpStatus.OK);
        }
    }

    @DeleteMapping({"/{receptionId}"})
    public ResponseEntity<?> delete(@PathVariable("receptionId") String receptionId) {
        RemoveReceptionCmd cmd = new RemoveReceptionCmd();
        ResponseMsgWithBoolean response = new ResponseMsgWithBoolean();
        ReceptionResponseFromQuery receptionResponseFromQuery = this.receptionService.getReception(receptionId);
        if (receptionResponseFromQuery.getReception() == null) {
            response.setResult(false);
            response.setMessage("Reception does not exist!");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        } else {
            cmd.setId(receptionResponseFromQuery.getReception().getReceptionId());

            try {
                this.commandGateway.sendAndWait(cmd);
                response.setMessage("Reception deleted successfully!");
                response.setResult(true);
            } catch (Exception e) {
                log.error("ReceptionServiceImpl -> deleteReception() : " + e.getMessage());
            }

            return new ResponseEntity(response, HttpStatus.OK);
        }
    }
}
