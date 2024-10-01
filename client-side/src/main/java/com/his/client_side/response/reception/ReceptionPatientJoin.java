package com.his.client_side.response.reception;


import com.his.client_side.model.reception.VitalSign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionPatientJoin {
    private String receptionId;
    private String patientId;
    private String firstName;
    private String lastName;
    private String nationalId;
    private Boolean emergency;
    private String doctorFullName;
    private String visitStatus;
    private String receptionStatus;
    private Date dateOfReception;
    private VitalSign vitalSign;
    private Date dob;
    private String gender;
    private String description;
}
