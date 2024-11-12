package com.his.client_side.model.pharmacy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prescription {
    private String prescriptionId;
    private String medicineName;
    private String instruction;
    private String dosage;
    private String measurementUnit;
    private String frequency;
    private String noOfDays;
}
