package com.patient_management.patient_core.event;




import com.patient_management.patient_core.model.Patient;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPatientCreatedEvent {
    private String id;
    private Patient patient;


}
