package com.reception_management.reception_cmd_ms.service;

import com.reception_management.reception_cmd_ms.dto.ReceptionDto;
import com.reception_management.reception_cmd_ms.dto.UpdateReceptionDto;
import com.reception_management.reception_core.model.Reception;
import com.reception_management.reception_core.repository.ReceptionRepository;
import com.reception_management.reception_core.responeObj.ReceptionResponseFromQuery;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Random;

@Service
public class ReceptionServiceImpl implements ReceptionService{
    private final ReceptionRepository receptionRepository;

    public ReceptionServiceImpl(ReceptionRepository receptionRepository) {
        this.receptionRepository = receptionRepository;
    }

    public String generateReceptionId() {
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

    public ReceptionResponseFromQuery getReception(String receptionId) {
        Reception reception = receptionRepository.findByReceptionId(receptionId);
        return reception == null
                ? new ReceptionResponseFromQuery((Reception)null, "Reception does not exist!")
                : new ReceptionResponseFromQuery(reception, "Reception exists!");
    }

    public Reception mapUpdateReceptionDtoToReception(UpdateReceptionDto updateReceptionDto) {
        if (updateReceptionDto == null) {
            throw new NullPointerException("updateReceptionDto is null");
        } else {

            Reception reception = new Reception();
            reception.setDateOfReception(updateReceptionDto.getDateOfReception());
            reception.setVitalSign(updateReceptionDto.getVitalSign());
            reception.setComment(updateReceptionDto.getComment());
            reception.setEmergency(updateReceptionDto.getEmergency());
            reception.setPatientId(updateReceptionDto.getPatientId());
            reception.setDoctorId(updateReceptionDto.getDoctorId());
            return reception;
        }
    }

    public Reception mapReceptionDtoToReception(ReceptionDto receptionDto) {
        if (receptionDto == null) {
            throw new NullPointerException("updateReceptionDto is null");
        } else {
            Reception reception = new Reception();
            String receptionId = this.generateReceptionId();
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            reception.setDateOfReception(date);
            reception.setReceptionId(receptionId);
            reception.setVitalSign(receptionDto.getVitalSign());
            reception.setComment(receptionDto.getComment());
            reception.setEmergency(receptionDto.getEmergency());
            reception.setPatientId(receptionDto.getPatientId());
            reception.setDoctorId(receptionDto.getDoctorId());
            return reception;
        }
    }
}
