package com.his.client_side.model.patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    private String patientId;
    private String firstName;
    private String lastName;
    private Date dob;
    private String nationalId;
    private String gender;
    private Integer age;
    private String email;
    private Address address;
    private List<Phone> phones;

    @Override
    public String toString() {
        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", nationalId='" + nationalId + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }
}
