package com.reception_management.reception_core.event;

import com.reception_management.reception_core.model.Reception;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionUpdatedEvent {
    private String id;
    private Reception reception;
}
