package com.patient_management.patient_cmd_ms.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemovePatientCmd {
    @TargetAggregateIdentifier
    private String id;
}