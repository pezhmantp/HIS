package com.reception_management.reception_cmd_ms.service;

import com.reception_management.reception_cmd_ms.dto.ReceptionDto;
import com.reception_management.reception_cmd_ms.dto.UpdateReceptionDto;
import com.reception_management.reception_cmd_ms.dto.VisitStatusDto;
import com.reception_management.reception_cmd_ms.kafka.event.VisitRemovedEvent;
import com.reception_management.reception_core.model.Reception;
import com.reception_management.reception_core.responeObj.ReceptionResponse;
import org.springframework.messaging.MessageHeaders;

import java.util.Properties;

public interface ReceptionService {
    String generateReceptionId();

    ReceptionResponse getReception(String receptionId);

    Reception mapUpdateReceptionDtoToReception(UpdateReceptionDto updateReceptionDto);

    Reception mapReceptionDtoToReception(ReceptionDto receptionDto);

    void changeVisitStatus(VisitStatusDto msg, MessageHeaders headers);
//    void removeReception(String receptionId);
    void removeReceptionTransaction(VisitRemovedEvent msg, MessageHeaders headers);
    Properties getConsumerProperties();
//    void removeReceptionTransaction(Boolean msg,MessageHeaders headers);

}
