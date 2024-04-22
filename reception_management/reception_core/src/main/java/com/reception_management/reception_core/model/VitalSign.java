package com.reception_management.reception_core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vital_sign_tbl")
public class VitalSign {
    @Id
    @Column(name = "vital_sign_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vitalSignId;
    @Column(name = "systolic")
    private Double systolic;
    @Column(name = "diastolic")
    private Double diastolic;
    @Column(name = "temperature")
    private Double temperature;
    @Column(name = "blood_oxygen")
    private Double bloodOxygen;
}
