package com.visit_management.visit_core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "visit_tbl")
public class Visit {
    @Id
    @Column(name = "visit_id")
    private String visitId;
    @Column(name = "visit_date")
    private Date visitDate;
    @Column(name = "reception_id")
    private String receptionId;
    @Column(name = "seen")
    private Boolean seen=false;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "visit_id")
    private List<Medication> medications;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "visit_id")
    private List<Investigation> investigations;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;
}
