package com.laboratory_management.laboratory_core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blood_test_tbl")
public class BloodTest extends Test{
    @Column(name = "hemoglobin")
    private Double hemoglobin;
    @Column(name = "rbc")
    private Double rbc;
    @Column(name = "pcv")
    private Double pcv;
    @Column(name = "mcv")
    private Double mcv;
    @Column(name = "mch")
    private Double mch;
    @Column(name = "wbc")
    private Integer wbc;
    @Column(name = "neutrophils")
    private Integer neutrophils;
    @Column(name = "lymphocytes")
    private Integer lymphocytes;
    @Column(name = "monocytes")
    private Integer monocytes;
    @Column(name = "eosinophiles")
    private Integer eosinophiles;
}
