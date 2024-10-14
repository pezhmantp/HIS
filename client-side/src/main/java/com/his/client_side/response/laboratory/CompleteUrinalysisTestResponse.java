package com.his.client_side.response.laboratory;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@JsonTypeName("urinalysisTest")
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
