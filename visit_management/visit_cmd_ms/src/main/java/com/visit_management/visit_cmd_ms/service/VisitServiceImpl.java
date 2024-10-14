package com.visit_management.visit_cmd_ms.service;

import com.visit_management.visit_cmd_ms.command.RemoveVisitCommand;
import com.visit_management.visit_cmd_ms.dto.UpdateVisitDto;
import com.visit_management.visit_cmd_ms.dto.VisitDto;
import com.visit_management.visit_cmd_ms.dto.VisitStatusDto;
import com.visit_management.visit_cmd_ms.kafka.config.KafkaConfigData;
import com.visit_management.visit_cmd_ms.kafka.event.VisitRemovedEvent;
import com.visit_management.visit_cmd_ms.kafka.producer.KafkaProducer;
import com.visit_management.visit_core.model.Visit;
import com.visit_management.visit_core.query.FindVisitByReceptionIdQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
public class VisitServiceImpl implements VisitService{

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private KafkaTemplate<String, VisitRemovedEvent> kafkaTemplate;
    @Autowired
    private KafkaTemplate<String, VisitStatusDto> visitStatusKafkaTemplate;
    @Autowired
    private QueryGateway queryGateway;
    private final KafkaProducer<String, VisitStatusDto> visitKafkaProducer;

    private final KafkaConfigData kafkaConfigData;
    private static String TOPIC="";

    public VisitServiceImpl(KafkaProducer<String, VisitStatusDto> visitKafkaProducer, KafkaTemplate<String, VisitStatusDto> kafkaTemplate, KafkaConfigData kafkaConfigData) {
        this.visitKafkaProducer = visitKafkaProducer;
//        this.visitRemovedKafkaProducer = visitRemovedKafkaProducer;
//        this.kafkaTemplate = kafkaTemplate;
        this.kafkaConfigData = kafkaConfigData;
    }

    @Override
    public String generateVisitId() {
        String RNDCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();

        while(sb.length() < 12) {
            int index = (int)(rnd.nextFloat() * (float)RNDCHARS.length());
            sb.append(RNDCHARS.charAt(index));
        }

        String rndStr = sb.toString();
        return rndStr;
    }

    @Override
    public String changeVisitStatus(String r,String receptionId,String visitStatus) throws ExecutionException, InterruptedException {
        VisitStatusDto visitStatusDto=new VisitStatusDto(receptionId,visitStatus);

//        this.visitKafkaProducer.send(kafkaConfigData.getChangeVisitStatusTopic(),receptionId,visitStatusDto);
        visitStatusKafkaTemplate.send(kafkaConfigData.getChangeVisitStatusTopic(),receptionId,visitStatusDto);
        return r;

    }
    @Override
    @KafkaListener(topics = "remove-visit-topic", groupId = "visit-topic-consumer", containerFactory = "kafkaListenerContainerFactory")
    public void removeVisit(String receptionId, MessageHeaders headers) throws ExecutionException, InterruptedException {
        System.out.println("removeVisit Consumerrrrrrrrrrrr " + receptionId);
        FindVisitByReceptionIdQuery query=new FindVisitByReceptionIdQuery();
        query.setReceptionId(receptionId);
        String visitId=queryGateway.query(query, ResponseTypes.instanceOf(String.class)).join();
        if(visitId == null)
        {
            sendKafkaEvent(null,receptionId,true);
        }
        else {
            System.out.println("visitId Consumerrrrrrrrrrrr " + visitId);

            RemoveVisitCommand command=new RemoveVisitCommand();
            command.setId(visitId);
            try {
                commandGateway.send(command);
                sendKafkaEvent(visitId,receptionId,true);
            }
            catch (Exception e){
                sendKafkaEvent(visitId,receptionId,false);
            }
        }

    }

    public void sendKafkaEvent(String visitId,String receptionId,Boolean status) throws ExecutionException, InterruptedException {

        VisitRemovedEvent event=new VisitRemovedEvent(receptionId,true,status);
        SendResult<String, VisitRemovedEvent> result= kafkaTemplate.send(kafkaConfigData.getVisitRemovedTopic(),receptionId,event).get();
        System.out.println("VisitRemovedTopic :::::::::: " + result.getRecordMetadata().topic() +"   "+
                result.getRecordMetadata().partition() + "   "+
                result.getRecordMetadata().offset());
    }

}
