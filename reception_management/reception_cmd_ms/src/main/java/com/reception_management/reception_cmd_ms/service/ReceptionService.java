package com.reception_management.reception_cmd_ms.service;

import com.reception_management.reception_cmd_ms.dto.ReceptionDto;
import com.reception_management.reception_cmd_ms.dto.UpdateReceptionDto;
import com.reception_management.reception_core.model.Reception;
import com.reception_management.reception_core.responeObj.ReceptionResponseFromQuery;

public interface ReceptionService {
    String generateReceptionId();

    ReceptionResponseFromQuery getReception(String receptionId);

    Reception mapUpdateReceptionDtoToReception(UpdateReceptionDto updateReceptionDto);

    Reception mapReceptionDtoToReception(ReceptionDto receptionDto);
}
