package com.reception_management.reception_core.event;



import com.reception_management.reception_core.model.Reception;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewReceptionCreatedEvent {
    private String id;
    private Reception reception;
}
