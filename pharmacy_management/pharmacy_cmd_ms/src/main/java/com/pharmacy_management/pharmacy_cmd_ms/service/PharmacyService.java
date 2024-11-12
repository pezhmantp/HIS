package com.pharmacy_management.pharmacy_cmd_ms.service;

import com.pharmacy_management.pharmacy_cmd_ms.dto.MedicineDto;
import com.pharmacy_management.pharmacy_cmd_ms.dto.MedicineRequestDto;
import com.pharmacy_management.pharmacy_core.model.Medicine;
import com.pharmacy_management.pharmacy_core.model.MedicineRequest;

public interface PharmacyService {
    MedicineRequest mapMedicineRequestDtoToMedicineRequest(MedicineRequestDto medicationRequestDto);
    Medicine mapMedicineDtoToMedicine(String medicineName);
    String generateId();
}
