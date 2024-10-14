package com.his.client_side.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrinalysisTestDto {
    private String testId;
    private String visitId;
    private String status;
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
    private String type;
}