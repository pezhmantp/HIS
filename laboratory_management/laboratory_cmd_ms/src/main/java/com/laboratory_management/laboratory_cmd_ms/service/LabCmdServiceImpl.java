package com.laboratory_management.laboratory_cmd_ms.service;

import com.laboratory_management.laboratory_core.dto.BloodTestDto;
import com.laboratory_management.laboratory_core.dto.UrinalysisTestDto;
import com.laboratory_management.laboratory_core.model.BloodTest;
import com.laboratory_management.laboratory_core.model.UrinalysisTest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LabCmdServiceImpl implements LabCmdService{
    @Override
    public BloodTest mapBloodTestDtoToBloodTest(BloodTestDto bloodTestDto) {
        BloodTest bloodTest =new BloodTest();
        bloodTest.setTestId(bloodTestDto.getTestId());
        bloodTest.setEosinophiles(bloodTestDto.getEosinophiles());
        bloodTest.setMcv(bloodTestDto.getMcv());
        bloodTest.setMch(bloodTestDto.getMch());
        bloodTest.setLymphocytes(bloodTestDto.getLymphocytes());
        bloodTest.setMonocytes(bloodTestDto.getMonocytes());
        bloodTest.setHemoglobin(bloodTestDto.getHemoglobin());
        bloodTest.setNeutrophils(bloodTestDto.getNeutrophils());
        bloodTest.setPcv(bloodTestDto.getPcv());
        bloodTest.setRbc(bloodTestDto.getRbc());
        bloodTest.setWbc(bloodTestDto.getWbc());
        bloodTest.setStatus("completed");
        bloodTest.setVisitId(bloodTestDto.getVisitId());
        return bloodTest;
    }

    @Override
    public UrinalysisTest mapUrinalysisTestDtoToUrinalysisTest(UrinalysisTestDto urinalysisTestDto) {
        UrinalysisTest urinalysisTest = new UrinalysisTest();
        urinalysisTest.setPh(urinalysisTestDto.getPh());
        urinalysisTest.setColor(urinalysisTestDto.getColor());
        urinalysisTest.setBlood(urinalysisTestDto.getBlood());
        urinalysisTest.setBilirubin(urinalysisTestDto.getBilirubin());
        urinalysisTest.setAppearance(urinalysisTestDto.getAppearance());
        urinalysisTest.setGlucose(urinalysisTestDto.getGlucose());
        urinalysisTest.setKetones(urinalysisTestDto.getKetones());
        urinalysisTest.setLeukocyteEsterase(urinalysisTestDto.getLeukocyteEsterase());
        urinalysisTest.setNitrite(urinalysisTestDto.getNitrite());
        urinalysisTest.setProtein(urinalysisTestDto.getProtein());
        urinalysisTest.setSpecificGravity(urinalysisTestDto.getSpecificGravity());
        urinalysisTest.setUrobilinogen(urinalysisTestDto.getUrobilinogen());
        urinalysisTest.setVisitId(urinalysisTestDto.getVisitId());
        urinalysisTest.setTestId(urinalysisTestDto.getTestId());
        urinalysisTest.setStatus("completed");
        return urinalysisTest;
    }

    @Override
    public String generateTestId() {
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
