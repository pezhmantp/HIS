package com.pharmacy_management.pharmacy_core.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineRemovedEvent {
    private String id;
}
