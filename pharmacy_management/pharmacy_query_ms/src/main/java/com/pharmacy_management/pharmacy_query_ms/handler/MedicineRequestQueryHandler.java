package com.pharmacy_management.pharmacy_query_ms.handler;

import com.pharmacy_management.pharmacy_core.model.MedicineRequest;
import com.pharmacy_management.pharmacy_core.query.FindAllMedicineRequestsQuery;
import com.pharmacy_management.pharmacy_core.query.FindMedicineRequestQuery;
import com.pharmacy_management.pharmacy_core.response.MedicineRequestsResponse;

public interface MedicineRequestQueryHandler {
    MedicineRequestsResponse FindAllMedicineRequests(FindAllMedicineRequestsQuery query);
    MedicineRequest FindMedicineRequest(FindMedicineRequestQuery query);
}
