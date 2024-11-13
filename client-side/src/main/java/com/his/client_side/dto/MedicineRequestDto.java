package com.his.client_side.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineRequestDto {
    private String visitId;
    private List<PrescriptionDto> prescriptionsDto;
}