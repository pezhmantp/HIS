package com.patient_management.patient_query_ms.handlers;

import com.patient_management.patient_core.queries.FindPatientByNationalIdQuery;
import com.patient_management.patient_core.queries.FindPatientByPatientIdQuery;
import com.patient_management.patient_core.queries.FindPatientsByPatientsIdsQuery;
import com.patient_management.patient_core.responeObj.PatientResponse;
import com.patient_management.patient_core.responeObj.PatientsResponse;

public interface PatientQueryHandler {
    PatientResponse findPatientByNationalId(FindPatientByNationalIdQuery query);
    PatientsResponse findPatientsByPatientIds(FindPatientsByPatientsIdsQuery query);
    PatientResponse findPatientByPatientId(FindPatientByPatientIdQuery query);
}
