package com.his.client_side.response;

import com.his.client_side.model.reception.Reception;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionResponse {

    private Reception reception;
    private String message;

}
