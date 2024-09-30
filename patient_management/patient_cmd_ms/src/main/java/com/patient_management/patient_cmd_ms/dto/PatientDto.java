package com.patient_management.patient_cmd_ms.dto;


import com.patient_management.patient_core.model.Address;
import com.patient_management.patient_core.model.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {

    private String firstName;
    private String lastName;
    private Date dob;
    private String nationalId;
    private String gender;
    private Integer age;
    private String email;
    private Address address;
    private List<Phone> phones;
}
