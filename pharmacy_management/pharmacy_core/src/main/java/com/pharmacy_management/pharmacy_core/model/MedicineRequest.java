package com.pharmacy_management.pharmacy_core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicine_request_tbl")
public class MedicineRequest {
    @Id
    @Column(name = "medicine_request_id")
    private String medicineRequestId;
    @Column(name = "visit_id")
    private String visitId;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "medicine_request_id")
    private List<Prescription> prescriptions;
}
