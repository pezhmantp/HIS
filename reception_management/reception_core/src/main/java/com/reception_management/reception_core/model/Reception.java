package com.reception_management.reception_core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reception_tbl")
public class Reception {
    @Id
    @Column(name = "reception_id")
    private String receptionId;
    @Column(name = "patient_id")
    private String patientId;
    @Column(name = "emergency")
    private Boolean emergency;
    @Column(name = "visit_status")
    private String visitStatus="Not Seen";
    @Column(name = "reception_status")
    private String receptionStatus="open";
    @Column(name = "doctor_id")
    private String doctorId;
    @Column(name = "date_of_reception")
    private Date dateOfReception;
    @Column(name = "comment")
    private String comment;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "vital_sign_id")
    private VitalSign vitalSign;
}
