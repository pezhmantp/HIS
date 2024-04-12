package com.patient_management.patient_query_ms.handlers;

import com.patient_management.patient_core.queries.FindPatientByNationalIdQuery;
import com.patient_management.patient_core.responeObj.PatientResponse;

public interface PatientQueryHandler {
    PatientResponse findPatientByNationalId(FindPatientByNationalIdQuery query);
    //FindUserResponseMsg findAllAccounts(FindAllUsersQuery query);
  //  FindtstMsg findTstByName(FindTstQry query);
}
