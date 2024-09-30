package com.visit_management.visit_cmd_ms.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveVisitCommand {
    @TargetAggregateIdentifier
    private String id;
}