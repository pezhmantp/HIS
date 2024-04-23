package com.visit_management.visit_core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medication_tbl")
public class Medication {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "medication_id")
    private String medicationId;
    @Column(name = "dosage")
    private Double dosage;
    @Column(name = "qty")
    private Integer qty;
    @Column(name = "instruction")
    private String instruction;
}
