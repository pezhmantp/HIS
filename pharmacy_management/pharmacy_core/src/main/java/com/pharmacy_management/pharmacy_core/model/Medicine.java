package com.pharmacy_management.pharmacy_core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicine_tbl")
public class Medicine {
    @Id
    @Column(name = "medicine_id")
    private String medicineId;
    @Column(name = "name")
    private String name;
}