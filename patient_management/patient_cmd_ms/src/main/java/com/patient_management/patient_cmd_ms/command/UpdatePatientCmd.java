package com.patient_management.patient_cmd_ms.command;

import com.patient_management.patient_core.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePatientCmd {
    @TargetAggregateIdentifier
    private String id;
    private Patient patient;
}