package com.his.client_side.model.patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Phone {
    private String phoneNumber;

    @Override
    public String toString() {
        return "Phone{" +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
