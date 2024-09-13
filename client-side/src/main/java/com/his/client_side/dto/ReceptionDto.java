package com.his.client_side.dto;

import com.his.client_side.model.reception.VitalSign;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionDto {
    private String patientId;
    private Boolean emergency;
    @NotEmpty(message = "Please select a doctor")
    private String doctorId;
//  @Valid
    private VitalSign vitalSign;
    private String comment;
}