package com.reception_management.reception_core.responeObj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchedPatient {
    private String patientNationalId;
    private String patientFN;
    private String patientLN;
    private Integer patientAge;
}
