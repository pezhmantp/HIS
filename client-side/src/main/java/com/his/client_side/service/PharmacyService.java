package com.his.client_side.service;

import com.his.client_side.model.pharmacy.Medicine;
import com.his.client_side.response.pharmacy.MedicinesResponse;

import java.util.List;

public interface PharmacyService {
    MedicinesResponse getMedicineList(String jwtAccessToken);
}
