package com.his.client_side.response;

import com.his.client_side.model.reception.Reception;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionsResponse {
    List<Reception> receptions;
    String message;
}
