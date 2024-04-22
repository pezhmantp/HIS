package com.reception_management.reception_core.responeObj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionResponse {

    private FetchedPatient fetchedPatient;
    private FetchedDoctor fetchedDoctor;
    private String receptionId;
    private String department;
    private String message;
    public ReceptionResponse(String message){
        this.message=message;
    }
}
