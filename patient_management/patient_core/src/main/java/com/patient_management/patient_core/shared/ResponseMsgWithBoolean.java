package com.patient_management.patient_core.shared;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMsgWithBoolean extends Message{
    private Boolean result;
}
