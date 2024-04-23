package com.reception_management.reception_query_ms.handler;

import com.reception_management.reception_core.queries.FindReceptionByReceptionIdQuery;
import com.reception_management.reception_core.responeObj.ReceptionResponseFromQuery;

public interface ReceptionQueryHandler {
    ReceptionResponseFromQuery findReceptionByReceptionId(FindReceptionByReceptionIdQuery query);
}
