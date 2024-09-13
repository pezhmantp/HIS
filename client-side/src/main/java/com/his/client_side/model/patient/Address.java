package com.his.client_side.model.patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String province;
    private String city;
    private String postalCode;
    private String restOfAddress;
    private String houseNo;

    @Override
    public String toString() {
        return "Address{" +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", restOfAddress='" + restOfAddress + '\'' +
                ", houseNo='" + houseNo + '\'' +
                '}';
    }
}
