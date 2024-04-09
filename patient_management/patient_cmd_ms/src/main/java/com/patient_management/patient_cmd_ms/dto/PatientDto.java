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
    private String sex;
    private Integer age;
    private String email;
    private Address address;
    private List<Phone> phones;
}




//    List<Phone> phones=new ArrayList<>();
//    Phone phone1=new Phone();
//    Phone phone2=new Phone();
//        phone1.setPhoneNumber("1111111");
//                phone2.setPhoneNumber("2222222");
//                phones.add(phone1);
//                phones.add(phone2);
//
//                Address address=new Address();
//                address.setProvince("province1");
//                address.setCity("city1");
//                address.setHouseNo("a1");
//                address.setPostalCode("a11111");
//
//                PatientDto patientDto=new PatientDto();
//                patientDto.setPersonalId("1234L56t");
//                patientDto.setFirstName("mohammad");
//                patientDto.setLastName("Akram");
//                patientDto.setAge(27);
//                patientDto.setDob(new Date(1988-01-22));
//                patientDto.setEmail("mohammad@gmail.com");
//                patientDto.setSex(Sex.MAN);
//                patientDto.setBloodType(BloodType.A_POS);
//                patientDto.setPhones(phones);
//                patientDto.setAddress(address);