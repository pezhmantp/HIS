package com.his.client_side.response.laboratory;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CompleteBloodTestResponse.class, name = "bloodTest"),
        @JsonSubTypes.Type(value = CompleteUrinalysisTestResponse.class, name = "urinalysisTest")
})
public abstract class CompleteTestResponse {
    private String testId;
    private String visitId;
    private String status;

    protected String className;
}
