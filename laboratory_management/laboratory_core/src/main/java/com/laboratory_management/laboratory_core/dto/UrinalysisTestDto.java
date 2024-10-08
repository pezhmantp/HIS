package com.laboratory_management.laboratory_core.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("urinalysisTestDto")
public class UrinalysisTestDto extends TestDto{
    private String color;
    private String appearance;
    private Double ph;
    private Double specificGravity;
    private String glucose;
    private String ketones;
    private String protein;
    private String bilirubin;
    private Double urobilinogen;
    private String blood;
    private String leukocyteEsterase;
    private String nitrite;
}
