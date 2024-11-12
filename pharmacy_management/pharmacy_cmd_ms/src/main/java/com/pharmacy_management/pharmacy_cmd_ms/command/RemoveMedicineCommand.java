package com.pharmacy_management.pharmacy_cmd_ms.command;

import com.pharmacy_management.pharmacy_core.model.Medicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveMedicineCommand {
    @TargetAggregateIdentifier
    private String id;
}
