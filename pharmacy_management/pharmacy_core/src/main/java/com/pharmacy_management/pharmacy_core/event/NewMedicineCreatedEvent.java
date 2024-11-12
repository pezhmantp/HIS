package com.pharmacy_management.pharmacy_core.event;

import com.pharmacy_management.pharmacy_core.model.Medicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewMedicineCreatedEvent {
    private String id;
    private Medicine medicine;
}
