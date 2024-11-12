package com.pharmacy_management.pharmacy_query_ms.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmacy_management.pharmacy_core.model.Medicine;
import com.pharmacy_management.pharmacy_core.query.FindAllMedicinesQuery;
import com.pharmacy_management.pharmacy_core.query.FindMedicineByNameQuery;
import com.pharmacy_management.pharmacy_core.repository.MedicineRepository;
import com.pharmacy_management.pharmacy_core.response.MedicinesResponse;
import org.axonframework.queryhandling.QueryHandler;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MedicineQueryHandlerImpl implements MedicineQueryHandler{

    @Autowired
    private MedicineRepository medicineRepository;
    private DozerBeanMapper mapper;
    @Override
    @QueryHandler
    public MedicinesResponse FindAllMedicines(FindAllMedicinesQuery query){
        List<Medicine> medicinesList = medicineRepository.findAll();

//        List<Medicine> medicines = new ArrayList<>();
//        mapper = new DozerBeanMapper();
//        medicinesList.forEach((p) -> {
//            Medicine medicine = mapper.map(p, Medicine.class);
//            medicines.add(medicine);
//        });
//        mapper = new DozerBeanMapper();
//        medicinesList = medicinesList.stream()
////                .filter(Objects::nonNull)
//                .map(medicine -> mapper.map(medicine, Medicine.class))
//                .collect(Collectors.toList());

//        return medicineRepository.findAll();
        return new MedicinesResponse(medicinesList);
    }

    @QueryHandler
    @Override
    public String FindMedicineByName(FindMedicineByNameQuery query) {
        Medicine medicine=medicineRepository.findByName(query.getMedicineName());
        return medicine.getName();
    }
}
