package com.laboratory_management.laboratory_core.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteBloodTestResponse extends CompleteTestResponse{
    private String type;
    private Double hemoglobin;
    private Double rbc;
    private Double pcv;
    private Double mcv;
    private Double mch;
    private Integer wbc;
    private Integer neutrophils;
    private Integer lymphocytes;
    private Integer monocytes;
    private Integer eosinophiles;
}
