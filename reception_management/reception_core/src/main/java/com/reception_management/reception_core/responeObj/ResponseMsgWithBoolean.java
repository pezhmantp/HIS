package com.reception_management.reception_core.responeObj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMsgWithBoolean extends Message{
    private Boolean result;
}
