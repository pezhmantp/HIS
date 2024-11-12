package com.reception_management.reception_cmd_ms.service;

import com.reception_management.reception_cmd_ms.command.RemoveReceptionCmd;
import com.reception_management.reception_cmd_ms.command.UpdateReceptionCmd;
import com.reception_management.reception_cmd_ms.dto.ReceptionDto;
import com.reception_management.reception_cmd_ms.dto.UpdateReceptionDto;
import com.reception_management.reception_cmd_ms.dto.VisitStatusDto;
import com.reception_management.reception_cmd_ms.kafka.config.KafkaConfigData;
import com.reception_management.reception_cmd_ms.kafka.consumer.KafkaConsumerConfig;
import com.reception_management.reception_cmd_ms.kafka.consumer.KafkaConsumerConfigData;
import com.reception_management.reception_cmd_ms.kafka.event.VisitRemovedEvent;
import com.reception_management.reception_core.model.Reception;
import com.reception_management.reception_core.queries.FindReceptionByReceptionIdQuery;
import com.reception_management.reception_core.repository.ReceptionRepository;
import com.reception_management.reception_core.responeObj.ReceptionResponse;
import com.reception_management.reception_core.responeObj.ResponseMsgWithBoolean;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Properties;
import java.util.Random;

@Service
public class ReceptionServiceImpl implements ReceptionService {
    private final ReceptionRepository receptionRepository;
    private static final Logger log = LoggerFactory.getLogger(ReceptionServiceImpl.class);
    @Autowired
    private KafkaTemplate<String, String> removeVisitKafkaTemplate;
    private KafkaTemplate<String, Boolean> receptionRemovedKafkaTemplate;
    @Autowired
    private KafkaConfigData kafkaConfigData;
    @Autowired
    private KafkaConsumerConfigData kafkaConsumerConfigData;
    @Autowired
    private KafkaConsumerConfig kafkaConsumerConfig;
    @Autowired
    private QueryGateway queryGateway;
    @Autowired
    private CommandGateway commandGateway;


    private boolean visitRemovedResponse=false;
    private boolean visitRemovedResult=false;

    public ReceptionServiceImpl(ReceptionRepository receptionRepository, KafkaTemplate<String, Boolean> receptionRemovedKafkaTemplate) {
        this.receptionRepository = receptionRepository;
        this.receptionRemovedKafkaTemplate = receptionRemovedKafkaTemplate;
    }

    public String generateReceptionId() {
        String RNDCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();

        while (sb.length() < 12) {
            int index = (int) (rnd.nextFloat() * (float) RNDCHARS.length());
            sb.append(RNDCHARS.charAt(index));
        }

        String rndStr = sb.toString();
        return rndStr;
    }

    public ReceptionResponse getReception(String receptionId) {
//        Reception reception = receptionRepository.findByReceptionId(receptionId);
        FindReceptionByReceptionIdQuery query = new FindReceptionByReceptionIdQuery(receptionId);
        ReceptionResponse receptionResponse = queryGateway.query(query, ResponseTypes.instanceOf(ReceptionResponse.class)).join();
//        Reception reception = receptionRepository.findByReceptionId(receptionId);
//        return reception == null
//                ? new ReceptionResponse((Reception)null, "Reception does not exist!")
//                : new ReceptionResponse(reception, "Reception exists!");
        return receptionResponse;
    }

    public Reception mapUpdateReceptionDtoToReception(UpdateReceptionDto updateReceptionDto) {
        if (updateReceptionDto == null) {
            throw new NullPointerException("updateReceptionDto is null");
        } else {

            Reception reception = new Reception();
            reception.setDateOfReception(updateReceptionDto.getDateOfReception());
            reception.setVitalSign(updateReceptionDto.getVitalSign());
            reception.setDescription(updateReceptionDto.getDescription());
            reception.setEmergency(updateReceptionDto.getEmergency());
            reception.setPatientId(updateReceptionDto.getPatientId());
            reception.setDoctorId(updateReceptionDto.getDoctorId());
            reception.setDescription(updateReceptionDto.getDescription());
            return reception;
        }
    }

    public Reception mapReceptionDtoToReception(ReceptionDto receptionDto) {
        if (receptionDto == null) {
            throw new NullPointerException("updateReceptionDto is null");
        } else {
            Reception reception = new Reception();
            String receptionId = this.generateReceptionId();
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            reception.setDateOfReception(date);
            reception.setReceptionId(receptionId);
            reception.setVitalSign(receptionDto.getVitalSign());
            reception.setDescription(receptionDto.getDescription());
            reception.setEmergency(receptionDto.getEmergency());
            reception.setPatientId(receptionDto.getPatientId());
            reception.setDoctorId(receptionDto.getDoctorId());
            reception.setDescription(receptionDto.getDescription());
            return reception;
        }
    }


    @Override
    @KafkaListener(topics = "change-visit-status-topic", groupId = "visit-topic-consumer", containerFactory = "changeVisitStatusTopicKafkaListener")
    public void changeVisitStatus(VisitStatusDto msg, MessageHeaders headers) {
        UpdateReceptionCmd cmd = new UpdateReceptionCmd();
        ReceptionResponse receptionResponseFromQuery = this.getReception(msg.getReceptionId());
        System.out.println("$$$$$$$$$$$$$$$$$$ " + receptionResponseFromQuery.getReception());
        receptionResponseFromQuery.getReception().setVisitStatus(msg.getVisitStatus());
        cmd.setId(msg.getReceptionId());
        cmd.setReception(receptionResponseFromQuery.getReception());
        this.commandGateway.sendAndWait(cmd);
    }

//    @Override
@KafkaListener(topics = "visit-removed-topic", groupId = "visit-topic-consumer", containerFactory = "visitRemovedTopicKafkaListener")
@Override
public void removeReceptionTransaction(VisitRemovedEvent msg, MessageHeaders headers) {
//    removeVisitKafkaTemplate.send(kafkaConfigData.getRemoveVisitTopic(),receptionId,receptionId);


    if(msg.getEventPublished() == false)
    {
            removeVisitKafkaTemplate.send(kafkaConfigData.getRemoveVisitTopic(),msg.getReceptionId(),msg.getReceptionId());
            System.out.println("msg ////////////////////// " +msg);
    }
    else {
        if (msg.getStatus()) {
          boolean result = removeReception(msg.getReceptionId());
          if(result)
          {
              receptionRemovedKafkaTemplate.send(kafkaConfigData.getReceptionRemovedTopic(),msg.getReceptionId(),true);
          }
          else {
              receptionRemovedKafkaTemplate.send(kafkaConfigData.getReceptionRemovedTopic(),msg.getReceptionId(),false);
          }
        }
        else {
            System.out.println("Err on removing visit occurred ////////////////////// " +msg.getStatus());
//            return false;
        }
    }
//    return false;
    }

    @Override
    public Properties getConsumerProperties() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigData.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerConfigData.getConsumerGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConsumerConfigData.getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConsumerConfigData.getValueDeserializer());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerConfigData.getAutoOffsetReset());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, kafkaConsumerConfigData.getTrustedPackage());
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Boolean.class);
        return props;
    }

    private Boolean removeReception(String receptionId){
        System.out.println("%%%%%%%%% removeReception() called");
    RemoveReceptionCmd cmd = new RemoveReceptionCmd();
    ResponseMsgWithBoolean response = new ResponseMsgWithBoolean();
    ReceptionResponse receptionResponseFromQuery = getReception(receptionId);
    if (receptionResponseFromQuery.getReception() == null) {
        return false;
//            response.setMessage("Reception does not exist!");

    } else {
        cmd.setId(receptionResponseFromQuery.getReception().getReceptionId());

        try {
            this.commandGateway.sendAndWait(cmd);
            return true;
//                response.setMessage("Reception deleted successfully!");
//                response.setResult(true);
        } catch (Exception e) {
            log.error("ReceptionServiceImpl -> removeReception() : " + e.getMessage());
        }
    }
    return false;
}


//    @KafkaListener(topics = "visit-removed-topic", groupId = "visit-topic-consumer", containerFactory = "visitRemovedTopicKafkaListener")
//    @Override
//    public void removeReceptionTransaction(VisitRemovedEvent msg, MessageHeaders headers){
//
//        if(msg.getStatus())
//        {
//            removeReception(msg.getReceptionId());
//        }
//        else {
//            log.error("Error occurred on removing reception");
//        }
//
//
////            System.out.println(">>>>>>>> visit-removed-topic : " + msg);
//
//
////        System.out.println("########## visitRemovedResponse: " + visitRemovedResponse);
////
//////        System.out.println("########## Visit removed: " + visitRemovedResponse);
////        System.out.println("########## Visit removed result: " + visitRemovedResult);
//    }

//    @KafkaListener(topics = "visit-removed-topic", groupId = "visit-topic-consumer", containerFactory = "visitRemovedTopicKafkaListener")
//    public void onVisitRemovedResponse(Boolean msg,MessageHeaders headers){
//        System.out.println(">>>>>>>> visit-removed-topic : " + msg);
//        visitRemovedResponse = true;
//        visitRemovedResult = msg;
//    }

//    private void removeVisit() {
//        KafkaConsumer<String,String> kafkaConsumer;
//        kafkaConsumer.subscribe("");
//    }


}

//    @Override
//    @KafkaListener(topics = "change-visit-status-topic", groupId = "visit-topic-consumer", containerFactory = "kafkaListenerContainerFactory")
//    public void changeVisitStatus(VisitStatusDto msg, MessageHeaders headers) {
//        UpdateReceptionCmd cmd = new UpdateReceptionCmd();
//        ReceptionResponse receptionResponseFromQuery = this.getReception(msg.getReceptionId());
//
//        receptionResponseFromQuery.getReception().setVisitStatus(msg.getVisitStatus());
//        cmd.setId(msg.getReceptionId());
//        cmd.setReception(receptionResponseFromQuery.getReception());
////       Reception toBeUpdatedReception = this.mapUpdateReceptionDtoToReception(updateReceptionDto);
////       toBeUpdatedReception.setReceptionId(updateReceptionDto.getReceptionId());
//        receptionResponseFromQuery.getReception().setReceptionStatus("completed");
//        cmd.setReception(receptionResponseFromQuery.getReception());
//        this.commandGateway.sendAndWait(cmd);
//    }