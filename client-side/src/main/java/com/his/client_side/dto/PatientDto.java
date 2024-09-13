package com.his.client_side.dto;

import com.his.client_side.model.patient.Address;
import com.his.client_side.model.patient.Phone;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    @NotEmpty(message = "National Id can not be empty")
    @Size(max = 15,message = "Maximum 15 characters allowed")
    private String nationalId;
    @NotEmpty(message = "First name can not be empty")
    @Size(max = 40,message = "Maximum 40 characters allowed")
    private String firstName;
    @NotEmpty(message = "Last name can not be empty")
    @Size(max = 40,message = "Maximum 40 characters allowed")
    private String lastName;
    @NotNull(message = "Date of birth can not be empty")
    @Past(message = "Feature date is not allowed")
    private Date dob;
    @NotEmpty(message = "Gender can not be empty")
    private String gender;
    private Integer age;
    @Email(message = "Email should be valid")
    private String email;
    private Address address;
    private List<Phone> phones;
}
