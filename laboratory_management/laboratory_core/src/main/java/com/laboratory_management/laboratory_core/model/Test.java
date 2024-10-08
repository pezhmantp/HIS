package com.laboratory_management.laboratory_core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Test {
    @Id
    @Column(name = "test_id")
    private String testId;
    @Column(name = "visit_id")
    private String visitId;
    @Column(name = "status")
    private String status;
}
