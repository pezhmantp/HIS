package com.his.client_side.model.reception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VitalSign {
    private Long vitalSignId;
    private Double systolic;
    private Double diastolic;
    private Double temperature;
    private Double bloodOxygen;
}