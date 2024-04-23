package com.visit_management.visit_core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "investigation_tbl")
public class Investigation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "investigation_id")
    private String investigationId;
    @Column(name = "investigation_status")
    private String investigationStatus;
}
