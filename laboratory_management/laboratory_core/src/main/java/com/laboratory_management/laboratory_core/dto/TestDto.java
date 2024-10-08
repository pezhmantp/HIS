package com.laboratory_management.laboratory_core.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonTypeInfo (use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes ({
        @JsonSubTypes.Type(value = BloodTestDto.class, name = "bloodTestDto"),
        @JsonSubTypes.Type(value = UrinalysisTestDto.class, name = "urinalysisTestDto")
})
public abstract class TestDto {
//    @JsonProperty("testId")
    private String testId;
//    @JsonProperty("visitId")
    private String visitId;
//    @JsonProperty("status")
    private String status;
}
