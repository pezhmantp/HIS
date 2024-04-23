package com.visit_management.visit_core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "diagnosis_tbl")
public class Diagnosis {
    @Id
    @Column(name = "diagnosis_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diagnosisId;
    @Column(name = "attendance")
    private String attendance;
    @Column(name = "type_of_disease")
    private String typeOfDisease;
    @Column(name = "additional_comment")
    private String additionalComment;
}