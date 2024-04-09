package com.patient_management.patient_cmd_ms.controller;

import com.patient_management.patient_core.model.Patient;
import com.patient_management.patient_core.responeObj.PatientResponse;
import com.patient_management.patient_core.shared.ResponseMsgWithBoolean;
import com.patient_management.patient_cmd_ms.command.NewPatientCmd;
import com.patient_management.patient_cmd_ms.command.RemovePatientCmd;
import com.patient_management.patient_cmd_ms.command.UpdatePatientCmd;
import com.patient_management.patient_cmd_ms.dto.PatientDto;
import com.patient_management.patient_cmd_ms.service.PatientService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/patient")
public class PatientCmdController {


    private static final Logger log= LoggerFactory.getLogger(PatientCmdController.class);

    private final PatientService commonService;
    private final CommandGateway commandGateway;




    public PatientCmdController(PatientService commonService, CommandGateway commandGateway) {
        this.commonService = commonService;
        this.commandGateway = commandGateway;

    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody PatientDto patientDto){
        PatientResponse patientResponse=new PatientResponse();
        log.info("add() -> patientDto received : " + patientDto.getNationalId());

        PatientResponse response =  commonService.fetchPatient(patientDto.getNationalId());

        if(response.getPatient() != null){
            patientResponse.setPatient(response.getPatient());
            patientResponse.setMessage("Patient already exists!");
            log.info("add() -> Patient already exists : " + response.getPatient());
            return new ResponseEntity<>(patientResponse, HttpStatus.FOUND);
        }
        Patient newPatient = commonService.mapPatientDtoToPatient(patientDto);
        String patientId=commonService.generatePatientId();

        newPatient.setPatientId(patientId); // controller
        System.out.println("---newPatient : "+newPatient);
        NewPatientCmd cmd =new NewPatientCmd(); // controller
        cmd.setId(newPatient.getPatientId());// ""
        cmd.setPatient(newPatient);// ""
        try {
            log.info("add(PatientDto) before commandGateway.sendAndWait(): " + newPatient);
            commandGateway.sendAndWait(cmd);
            patientResponse.setPatient(newPatient);
            patientResponse.setMessage("New patient saved");
            log.info("add(PatientDto): New patient saved: " + newPatient);
        }catch (Exception e){
            log.error("Error at add(PatientDto) " +
                    newPatient + " - " + e);
        }

        return new ResponseEntity<>(patientResponse, HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody PatientDto patientDto){
        UpdatePatientCmd cmd=new UpdatePatientCmd();
        PatientResponse response = commonService.fetchPatient(patientDto.getNationalId());
        if(response.getPatient() == null){
            return new ResponseEntity<>("Patient does not exist!", HttpStatus.NOT_FOUND);
        }

        try{
            cmd.setId(response.getPatient().getPatientId());
            log.info("update() -> Before commonService.mapPatientDtoToPatient");
            Patient toBeUpdatedPatient=commonService.mapPatientDtoToPatient((patientDto)); //serv
            toBeUpdatedPatient.setPatientId(response.getPatient().getPatientId()); // cnt

            cmd.setPatient(toBeUpdatedPatient);
            log.info("update() -> Before commandGateway.sendAndWait() " + cmd.toString());
            commandGateway.sendAndWait(cmd);

        }
        catch (Exception e){
            log.error("Error at update() : " + e.getMessage());
        }

        PatientResponse fetchUpdatedPatientResponse = commonService.fetchPatient(patientDto.getNationalId());
        return new ResponseEntity<>(fetchUpdatedPatientResponse.getPatient(), HttpStatus.OK);
    }

    @DeleteMapping("/{nationalId}")
    public ResponseEntity<?> delete(@PathVariable ("nationalId") String nationalId){
      //  RemovePatientCmd cmd=new RemovePatientCmd();
        ResponseMsgWithBoolean response=new ResponseMsgWithBoolean();
        PatientResponse fetchedPatientResponse = commonService.fetchPatient(nationalId);
        if(fetchedPatientResponse.getPatient() != null){ // cnt
            RemovePatientCmd cmd=new RemovePatientCmd(fetchedPatientResponse.getPatient().getPatientId());

            try {
                commandGateway.sendAndWait(cmd);
                response.setMessage("Patient deleted successfully!");
                response.setResult(true);
            }
            catch (Exception e){
                log.error("PatientController -> delete() : " + e.getMessage());
            }

            return new ResponseEntity<>(response.getMessage(),HttpStatus.OK);
        }
        else {
            response.setMessage("Patient does not exist!");
            response.setResult(true);
            return new ResponseEntity<>(response.getMessage(),HttpStatus.NOT_FOUND);
        }

    }
}
