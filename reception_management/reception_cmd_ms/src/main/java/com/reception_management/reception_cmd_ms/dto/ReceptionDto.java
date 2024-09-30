package com.reception_management.reception_cmd_ms.dto;

import com.reception_management.reception_core.model.VitalSign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionDto {
    private String patientId;
    private Boolean emergency;
    private String visitStatus;
    private String receptionStatus;
    private String doctorId;
    private VitalSign vitalSign;
    private String description;
}
