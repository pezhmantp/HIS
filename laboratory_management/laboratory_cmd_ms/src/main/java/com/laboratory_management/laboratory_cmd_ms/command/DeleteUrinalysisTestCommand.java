package com.laboratory_management.laboratory_cmd_ms.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUrinalysisTestCommand {
    @TargetAggregateIdentifier
    private String id;
}
