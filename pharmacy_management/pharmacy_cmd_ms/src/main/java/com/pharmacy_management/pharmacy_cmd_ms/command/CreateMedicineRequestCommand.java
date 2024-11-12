package com.pharmacy_management.pharmacy_cmd_ms.command;

import com.pharmacy_management.pharmacy_core.model.MedicineRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMedicineRequestCommand {
    @TargetAggregateIdentifier
    private String id;
    private MedicineRequest medicineRequest;
}
