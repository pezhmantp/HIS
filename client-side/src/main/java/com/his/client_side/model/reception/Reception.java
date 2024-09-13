package com.his.client_side.model.reception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reception {
    private String receptionId;
    private String patientId;
    private Boolean emergency;
    private String visitStatus;
    private String receptionStatus;
    private String doctorId;
    private Date dateOfReception;
    private String comment;
    private String description;
    private VitalSign vitalSign;
}
