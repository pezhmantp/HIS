package com.laboratory_management.laboratory_cmd_ms.command;

import com.laboratory_management.laboratory_core.model.BloodTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBloodTestCommand {
    @TargetAggregateIdentifier
    private String id;
    private BloodTest bloodTest;
}
