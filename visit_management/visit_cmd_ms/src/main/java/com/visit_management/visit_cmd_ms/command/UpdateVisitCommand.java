package com.visit_management.visit_cmd_ms.command;

import com.visit_management.visit_core.model.Visit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVisitCommand {
    @TargetAggregateIdentifier
    private String id;
    private Visit visit;
}