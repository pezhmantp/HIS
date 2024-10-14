package com.his.client_side.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodTestDto {
    private String testId;
    private String visitId;
    private String status;
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
    private String type;
}
