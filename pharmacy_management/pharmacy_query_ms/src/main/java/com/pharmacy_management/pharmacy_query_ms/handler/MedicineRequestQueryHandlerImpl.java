package com.pharmacy_management.pharmacy_query_ms.handler;

import com.pharmacy_management.pharmacy_core.model.Medicine;
import com.pharmacy_management.pharmacy_core.model.MedicineRequest;
import com.pharmacy_management.pharmacy_core.query.FindAllMedicineRequestsQuery;
import com.pharmacy_management.pharmacy_core.query.FindAllMedicinesQuery;
import com.pharmacy_management.pharmacy_core.query.FindMedicineRequestQuery;
import com.pharmacy_management.pharmacy_core.repository.MedicineRepository;
import com.pharmacy_management.pharmacy_core.repository.MedicineRequestRepository;
import com.pharmacy_management.pharmacy_core.response.MedicineRequestsResponse;
import com.pharmacy_management.pharmacy_core.response.MedicinesResponse;
import org.axonframework.queryhandling.QueryHandler;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineRequestQueryHandlerImpl implements MedicineRequestQueryHandler{
    private DozerBeanMapper mapper;
    @Autowired
    private MedicineRequestRepository medicineRequestRepository;

    @Override
    @QueryHandler
    public MedicineRequestsResponse FindAllMedicineRequests(FindAllMedicineRequestsQuery query) {
        List<MedicineRequest> medicineRequests = medicineRequestRepository.findAll();
        mapper=new DozerBeanMapper();
        List<MedicineRequest> medicineRequests2= medicineRequests.stream().map(i -> mapper.map(i, MedicineRequest.class)).toList();
        return new MedicineRequestsResponse(medicineRequests2);
    }

    @Override
    @QueryHandler
    public MedicineRequest FindMedicineRequest(FindMedicineRequestQuery query) {
        MedicineRequest medicineRequest = medicineRequestRepository.findByMedicineRequestId(query.getMedicineRequestId());
        mapper=new DozerBeanMapper();
        MedicineRequest mappedMedicineRequest=mapper.map(medicineRequest,MedicineRequest.class);
//        mapper=new DozerBeanMapper();
//        List<MedicineRequest> medicineRequests2= medicineRequests.stream().map(i -> mapper.map(i, MedicineRequest.class)).toList();
        return mappedMedicineRequest;
    }
}
