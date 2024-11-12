package com.pharmacy_management.pharmacy_core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prescription_tbl")
public class Prescription {
    @Id
    @Column(name = "prescription_id")
    private String prescriptionId;
    @Column(name = "medicine_name")
    private String medicineName;
    @Column(name = "instruction")
    private String instruction;
    @Column(name = "dosage")
    private String dosage;
    @Column(name = "measurement_unit")
    private String measurementUnit;
    @Column(name = "frequency")
    private String frequency;
    @Column(name = "no_of_days")
    private String noOfDays;
}
