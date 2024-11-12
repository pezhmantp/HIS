package com.pharmacy_management.pharmacy_core.response;

import com.pharmacy_management.pharmacy_core.model.Medicine;
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
