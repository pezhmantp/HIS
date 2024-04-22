package com.reception_management.reception_cmd_ms.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveReceptionCmd {
    @TargetAggregateIdentifier
    private String id;
}