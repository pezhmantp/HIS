package com.his.client_side.response.pharmacy;

import com.his.client_side.model.pharmacy.MedicineRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicineRequestsResponse {
    List<MedicineRequest> medicineRequests;
    String message;
}
