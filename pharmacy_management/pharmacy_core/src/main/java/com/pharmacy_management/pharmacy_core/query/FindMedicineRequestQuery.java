package com.pharmacy_management.pharmacy_core.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindMedicineRequestQuery {
    private String medicineRequestId;
}