package com.pharmacy_management.pharmacy_core.event;

import com.pharmacy_management.pharmacy_core.model.MedicineRequest;
import com.pharmacy_management.pharmacy_core.model.Prescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewMedicineRequestCreatedEvent {
    private String id;
    private MedicineRequest medicineRequest;
}
