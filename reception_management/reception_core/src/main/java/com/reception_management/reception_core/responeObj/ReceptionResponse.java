package com.reception_management.reception_core.responeObj;

import com.reception_management.reception_core.model.Reception;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionResponse {

    private Reception reception;
    private String message;
}
