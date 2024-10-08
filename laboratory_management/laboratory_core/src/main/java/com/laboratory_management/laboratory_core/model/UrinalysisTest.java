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
@Table(name = "urinalysis_test_tbl")
public class UrinalysisTest extends Test{
    @Column(name = "color")
    private String color;
    @Column(name = "appearance")
    private String appearance;
    @Column(name = "ph")
    private Double ph;
    @Column(name = "specific_gravity")
    private Double specificGravity;
    @Column(name = "glucose")
    private String glucose;
    @Column(name = "ketones")
    private String ketones;
    @Column(name = "protein")
    private String protein;
    @Column(name = "bilirubin")
    private String bilirubin;
    @Column(name = "urobilinogen")
    private Double urobilinogen;
    @Column(name = "blood")
    private String blood;
    @Column(name = "leukocyte_esterase")
    private String leukocyteEsterase;
    @Column(name = "nitrite")
    private String nitrite;
}