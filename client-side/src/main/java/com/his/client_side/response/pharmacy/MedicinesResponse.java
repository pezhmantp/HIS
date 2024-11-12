package com.his.client_side.response.pharmacy;

import com.his.client_side.model.pharmacy.Medicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicinesResponse {
    List<Medicine> medicines;
}
