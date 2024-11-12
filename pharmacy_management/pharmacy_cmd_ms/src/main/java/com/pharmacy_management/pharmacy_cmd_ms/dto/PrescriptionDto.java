package com.pharmacy_management.pharmacy_cmd_ms.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDto {
    private String medicineName;
    private String instruction;
    private String dosage;
    private String measurementUnit;
    private String frequency;
    private String noOfDays;
}
