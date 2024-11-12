package com.his.client_side.model.pharmacy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineRequest {
    private String medicineRequestId;
    private String visitId;
    private List<Prescription> prescriptions;
}