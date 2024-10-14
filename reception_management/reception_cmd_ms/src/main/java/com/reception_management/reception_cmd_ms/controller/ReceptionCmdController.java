package com.reception_management.reception_cmd_ms.controller;


import com.reception_management.reception_cmd_ms.command.CreateReceptionCmd;
import com.reception_management.reception_cmd_ms.command.RemoveReceptionCmd;
import com.reception_management.reception_cmd_ms.command.UpdateReceptionCmd;
import com.reception_management.reception_cmd_ms.command.markReceptionAsCmpltdCmd;
import com.reception_management.reception_cmd_ms.dto.ReceptionDto;
import com.reception_management.reception_cmd_ms.dto.UpdateReceptionDto;
import com.reception_management.reception_cmd_ms.kafka.config.KafkaConfigData;
import com.reception_management.reception_cmd_ms.kafka.consumer.KafkaConsumerConfig;
import com.reception_management.reception_cmd_ms.kafka.consumer.KafkaConsumerConfigData;
import com.reception_management.reception_cmd_ms.kafka.event.VisitRemovedEvent;
import com.reception_management.reception_cmd_ms.service.ReceptionService;
import com.reception_management.reception_core.model.Reception;
import com.reception_management.reception_core.queries.FindReceptionByReceptionIdQuery;
import com.reception_management.reception_core.responeObj.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


@RestController
@RequestMapping({"/receptionCmd"})
public class ReceptionCmdController {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;
    @Autowired
    private KafkaConfigData kafkaConfigData;
    @Autowired
    private KafkaTemplate<String, String> removeVisitKafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(ReceptionCmdController.class);
    @Autowired
    private ReceptionService receptionService;
    @Value(value = "${reception-created-topic}")
    private String reception_created_topic;


    @PostMapping
    public ResponseEntity<?> add(@RequestBody ReceptionDto receptionDto) {
        log.info("add(): receptionDto received from client " + receptionDto.toString());
        ReceptionResponse receptionResponse=new ReceptionResponse();

        try {
            Reception reception = this.receptionService.mapReceptionDtoToReception(receptionDto);
            CreateReceptionCmd cmd = new CreateReceptionCmd();
            cmd.setId(reception.getReceptionId());
            cmd.setReception(reception);
            log.info("add(): before sending CreateReceptionCmd to CommandGateway " + receptionDto.toString());
            this.commandGateway.sendAndWait(cmd);
//            SendResult<String,String> kafkaTemplateResult=kafkaTemplate.send(reception_created_topic,reception.getReceptionId(),reception.getReceptionId()).get();
//            log.info("add(): message sent to kafka brokers: " + kafkaTemplateResult.getRecordMetadata().partition() +
//                    " " + kafkaTemplateResult.getRecordMetadata().topic());

            receptionResponse.setReception(reception);
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
        ReceptionResponse receptionResponseFromQuery = this.receptionService.getReception(updateReceptionDto.getReceptionId());
        if (receptionResponseFromQuery.getReception() == null) {
            return new ResponseEntity(receptionResponseFromQuery, HttpStatus.NOT_FOUND);
        } else {
            cmd.setId(receptionResponseFromQuery.getReception().getReceptionId());
            Reception toBeUpdatedReception = this.receptionService.mapUpdateReceptionDtoToReception(updateReceptionDto);
            toBeUpdatedReception.setReceptionId(updateReceptionDto.getReceptionId());
            cmd.setReception(toBeUpdatedReception);
            this.commandGateway.sendAndWait(cmd);
            ReceptionResponse response = this.receptionService.getReception(updateReceptionDto.getReceptionId());
            return new ResponseEntity(response.getReception(), HttpStatus.OK);
        }
    }
    @GetMapping("/changeVisitStatus/{receptionId}")
    public ResponseEntity<?> changeVisitStatus(@PathVariable String receptionId) {
        System.out.println("changeVisitStatusssssssssssssssssssssssssssssssssss");
        return new ResponseEntity("response", HttpStatus.OK);
    }
    @PatchMapping("/changeReceptionStatusToCompleted")
    public ResponseEntity<?> changeReceptionStatusToCompleted(@RequestBody String receptionId) {
        UpdateReceptionCmd cmd = new UpdateReceptionCmd();
        ReceptionResponse receptionResponseFromQuery = this.receptionService.getReception(receptionId);

        if (receptionResponseFromQuery.getReception() == null) {
            return new ResponseEntity(receptionResponseFromQuery, HttpStatus.NOT_FOUND);
        } else {
            cmd.setId(receptionResponseFromQuery.getReception().getReceptionId());
//            Reception toBeUpdatedReception = this.receptionService.mapUpdateReceptionDtoToReception(updateReceptionDto);
//            toBeUpdatedReception.setReceptionId(updateReceptionDto.getReceptionId());
            receptionResponseFromQuery.getReception().setReceptionStatus("completed");
            cmd.setReception(receptionResponseFromQuery.getReception());
            this.commandGateway.sendAndWait(cmd);
            ReceptionResponse response = this.receptionService.getReception(receptionId);
            System.out.println("################ : " + response);
            return new ResponseEntity(response, HttpStatus.OK);
        }
    }

    @DeleteMapping({"/{receptionId}"})
    public ResponseEntity<?> delete(@PathVariable("receptionId") String receptionId) {
        ResponseMsgWithBoolean response = new ResponseMsgWithBoolean();
        System.out.println("Delete visit request: " + receptionId);
//        receptionService.removeReceptionTransaction(null,null,receptionId);
//        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        VisitRemovedEvent event=new VisitRemovedEvent();
        event.setEventPublished(false);
        event.setReceptionId(receptionId);

        receptionService.removeReceptionTransaction(event,null);

        KafkaConsumer<String, Boolean> consumer = new KafkaConsumer<>(receptionService.getConsumerProperties());
        consumer.subscribe(Arrays.asList("reception-removed-topic"));

        while(true){
            ConsumerRecords<String, Boolean> records =
                    consumer.poll(Duration.ofMillis(500));

            for (ConsumerRecord<String, Boolean> record : records){
                System.out.println("????????????? record.key: " + record.key() + " record.value : " + record.value());
                if(record.key().equals(receptionId)){
                    if(record.value() == true)
                    {
                        System.out.println("????????????? reception: " + receptionId + " deleted : " + record.value());
                        response.setResult(true);
                        response.setMessage("Reception removed successfully");
                        return new ResponseEntity(response, HttpStatus.OK);
                    }
                    response.setResult(false);
                    response.setMessage("Reception did not removed");
                    return new ResponseEntity(response, HttpStatus.OK);
                }
            }
        }
//        removeVisitKafkaTemplate.send(kafkaConfigData.getRemoveVisitTopic(),receptionId,receptionId);

//        response.setResult(true);
//        response.setMessage("bbbbbbbb");
//            return new ResponseEntity(response, HttpStatus.OK);
        }
    }


