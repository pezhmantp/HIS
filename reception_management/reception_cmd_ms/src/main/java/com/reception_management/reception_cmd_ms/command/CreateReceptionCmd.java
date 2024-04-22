package com.reception_management.reception_cmd_ms.command;

import com.reception_management.reception_core.model.Reception;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReceptionCmd {
    @TargetAggregateIdentifier
    private String id;
    private Reception reception;
}
