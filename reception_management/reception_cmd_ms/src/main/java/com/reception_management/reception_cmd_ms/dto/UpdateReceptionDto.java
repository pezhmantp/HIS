package com.reception_management.reception_cmd_ms.dto;

import com.reception_management.reception_core.model.VitalSign;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class UpdateReceptionDto extends ReceptionDto{
    private String receptionId;
    private Date dateOfReception;
    public UpdateReceptionDto(String patientId, Boolean emergency, String doctorId, VitalSign vitalSign,
                              String comment, String receptionId,Date dateOfReception) {
        super(patientId, emergency, doctorId, vitalSign, comment);
        this.receptionId=receptionId;
        this.dateOfReception=dateOfReception;
    }

}
