package com.his.client_side.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDto {
    private String medicineName;
    private String dosage;
    private String measurementUnit;
    private String frequency;
    private String noOfDays;
    private String instruction;
}
