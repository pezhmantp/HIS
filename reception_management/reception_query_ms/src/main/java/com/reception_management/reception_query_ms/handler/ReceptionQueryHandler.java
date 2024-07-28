package com.reception_management.reception_query_ms.handler;

import com.reception_management.reception_core.queries.FindReceptionByReceptionIdQuery;
import com.reception_management.reception_core.queries.FindReceptionsByDoctorIdQuery;
import com.reception_management.reception_core.responeObj.ReceptionResponse;
import com.reception_management.reception_core.responeObj.ReceptionResponseFromQuery;
import com.reception_management.reception_core.responeObj.ReceptionsResponse;

public interface ReceptionQueryHandler {
    ReceptionResponse findReceptionByReceptionId(FindReceptionByReceptionIdQuery query);
    ReceptionsResponse findReceptionsByDoctorId(FindReceptionsByDoctorIdQuery query);
}
