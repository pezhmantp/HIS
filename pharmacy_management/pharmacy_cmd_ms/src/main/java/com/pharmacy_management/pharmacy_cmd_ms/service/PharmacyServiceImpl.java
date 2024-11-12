package com.pharmacy_management.pharmacy_cmd_ms.service;

import com.pharmacy_management.pharmacy_cmd_ms.dto.MedicineDto;
import com.pharmacy_management.pharmacy_cmd_ms.dto.MedicineRequestDto;
import com.pharmacy_management.pharmacy_core.model.Medicine;
import com.pharmacy_management.pharmacy_core.model.MedicineRequest;
import com.pharmacy_management.pharmacy_core.model.Prescription;
import com.pharmacy_management.pharmacy_core.query.FindMedicineByNameQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PharmacyServiceImpl implements PharmacyService{

    @Autowired
    private QueryGateway queryGateway;
    @Override
    public MedicineRequest mapMedicineRequestDtoToMedicineRequest(MedicineRequestDto medicationRequestDto) {
        MedicineRequest medicationRequest=new MedicineRequest();
        List<Prescription> prescriptions=new ArrayList<>();
        medicationRequestDto.getPrescriptionsDto().forEach(i ->{
            prescriptions.add(new Prescription(generateId(),getMedicineName(i.getMedicineName()),i.getInstruction(),
                    i.getDosage(),i.getMeasurementUnit(),i.getFrequency(),i.getNoOfDays()));
        });
        medicationRequest.setMedicineRequestId(generateId());
        medicationRequest.setVisitId(medicationRequestDto.getVisitId());
        medicationRequest.setPrescriptions(prescriptions);
        return medicationRequest;
    }

    private String getMedicineName(String medicineName){
        FindMedicineByNameQuery query=new FindMedicineByNameQuery(medicineName);
        return queryGateway.query(query, ResponseTypes.instanceOf(String.class)).join();
    }
    @Override
    public Medicine mapMedicineDtoToMedicine(String medicineName) {
        Medicine medicine=new Medicine();
        medicine.setMedicineId(generateId());
        medicine.setName(medicineName);
        return medicine;
    }

    @Override
    public String generateId() {
        String RNDCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();

        while(sb.length() < 12) {
            int index = (int)(rnd.nextFloat() * (float)RNDCHARS.length());
            sb.append(RNDCHARS.charAt(index));
        }

        String rndStr = sb.toString();
        return rndStr;
    }
}
