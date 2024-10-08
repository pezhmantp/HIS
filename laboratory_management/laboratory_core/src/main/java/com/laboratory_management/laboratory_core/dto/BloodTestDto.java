package com.laboratory_management.laboratory_core.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("bloodTestDto")
public class BloodTestDto extends TestDto {
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