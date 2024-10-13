package com.laboratory_management.laboratory_core.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteUrinalysisTestResponse extends CompleteTestResponse{
    private String type;
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
