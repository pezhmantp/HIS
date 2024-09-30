package com.patient_management.patient_core.queries;
//
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindPatientsByPatientsIdsQuery {
    private List<String> patientsId;
}
