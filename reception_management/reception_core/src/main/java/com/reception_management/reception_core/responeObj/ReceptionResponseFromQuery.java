package com.reception_management.reception_core.responeObj;

import com.reception_management.reception_core.model.Reception;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionResponseFromQuery {
    private Reception reception;
    private String message;
    public ReceptionResponseFromQuery(String message){
        this.message=message;
    }
}
