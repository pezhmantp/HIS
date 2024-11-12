package com.pharmacy_management.pharmacy_query_ms.handler;

import com.pharmacy_management.pharmacy_core.model.Medicine;
import com.pharmacy_management.pharmacy_core.query.FindAllMedicinesQuery;
import com.pharmacy_management.pharmacy_core.query.FindMedicineByNameQuery;
import com.pharmacy_management.pharmacy_core.response.MedicinesResponse;

import java.util.Collection;
import java.util.List;

public interface MedicineQueryHandler {
    MedicinesResponse FindAllMedicines(FindAllMedicinesQuery query);
    String FindMedicineByName(FindMedicineByNameQuery query);
}
