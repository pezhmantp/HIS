package com.his.client_side.response.laboratory;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Getter
//@Setter
@JsonTypeName("bloodTest")
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
