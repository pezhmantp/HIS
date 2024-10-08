package com.laboratory_management.laboratory_cmd_ms.service;

import com.laboratory_management.laboratory_core.dto.BloodTestDto;
import com.laboratory_management.laboratory_core.dto.UrinalysisTestDto;
import com.laboratory_management.laboratory_core.model.BloodTest;
import com.laboratory_management.laboratory_core.model.UrinalysisTest;

public interface LabCmdService {
    BloodTest mapBloodTestDtoToBloodTest(BloodTestDto bloodTestDto);
    UrinalysisTest mapUrinalysisTestDtoToUrinalysisTest(UrinalysisTestDto urinalysisTestDto);
    String generateTestId();
}
