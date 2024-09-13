package com.his.client_side.model.doctor;

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
@Entity
@Table(name = "doctor_tbl")
public class Doctor {
    @Id
    @Column(name = "doctor_id")
    private Integer doctorId;
    @Column(name = "doctor_kc_username")
    private String doctorKcUsername;
    @Column(name = "doctor_full_name")
    private String doctorFullName;
    @Column(name = "department")
    private String department;
}
