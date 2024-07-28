package com.reception_management.reception_cmd_ms.service;

import com.reception_management.reception_cmd_ms.dto.ReceptionDto;
import com.reception_management.reception_core.model.Reception;
import com.reception_management.reception_core.model.VitalSign;
import com.reception_management.reception_core.repository.ReceptionRepository;
import com.reception_management.reception_core.responeObj.ReceptionResponseFromQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReceptionServiceImplTest {
    @InjectMocks
    @Spy
    private ReceptionServiceImpl receptionService;
    @Mock
    private ReceptionRepository receptionRepository;

    ReceptionServiceImplTest() {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should find and return reception by receptionId")
    void shouldFindReceptionByReceptionId() {
        Long ms = System.currentTimeMillis();
        Reception actualReception = new Reception("mSv11Jh8l47t", "patientId", true,
                "Not Seen", "doctorId",
                new Date(ms), "comment",
                new VitalSign(1L, 13.5, 7.5, 35.5, 93.2));
        Mockito.when(this.receptionRepository.findByReceptionId("mSv11Jh8l47t")).thenReturn(actualReception);
        ReceptionResponseFromQuery response = this.receptionService.getReception("mSv11Jh8l47t");
        Assertions.assertNotNull(response.getReception());
        Assertions.assertEquals(response.getReception(), actualReception);
    }

    @Test
    @DisplayName("should not find any reception by receptionId")
    void shouldNotFindReceptionByReceptionId() {
        Long ms = System.currentTimeMillis();
        Reception actualReception = new Reception("mSv11Jh8l47t", "patientId", true,
                "Not Seen", "doctorId",
                new Date(ms), "comment",
                new VitalSign(1L, 13.5, 7.5, 35.5, 93.2));
        Mockito.when(this.receptionRepository.findByReceptionId("mSv11Jh8l47t")).thenReturn(actualReception);
        ReceptionResponseFromQuery response = this.receptionService.getReception("incorrectReceptionId");
        Assertions.assertNull(response.getReception());
        Assertions.assertNotEquals(response.getReception(), actualReception);
    }

    @Test
    @DisplayName("should generate new random receptionId with length of 12")
    void shouldGeneratePatientId() {
        String receptionId = this.receptionService.generateReceptionId();
        Assertions.assertNotNull(receptionId);
        Assertions.assertTrue(receptionId.length() == 12);
    }

    @Test
    @DisplayName("should map receptionDto to reception")
    void shouldMapReceptionDtoToReception() {
        ReceptionDto receptionDto = new ReceptionDto("123", true, "doctorId", new VitalSign(1L, 14.0, 9.0, 36.0, 92.0), "comment");
        Long ms = System.currentTimeMillis();
        Reception actualReception = new Reception("12w5Df345", "123", true,"Not Seen",
                "doctorId", new Date(ms), "comment",
                new VitalSign(1L, 14.0, 9.0, 36.0, 92.0));
        Mockito.when(this.receptionService.mapReceptionDtoToReception(receptionDto)).thenReturn(actualReception);
        Reception expectedReception = this.receptionService.mapReceptionDtoToReception(receptionDto);
        Assertions.assertEquals(expectedReception.getPatientId(), actualReception.getPatientId());
        Assertions.assertEquals(expectedReception.getReceptionId(), actualReception.getReceptionId());
        Assertions.assertEquals(expectedReception.getDateOfReception(), actualReception.getDateOfReception());
        Assertions.assertEquals(expectedReception.getComment(), actualReception.getComment());
        Assertions.assertEquals(expectedReception.getEmergency(), actualReception.getEmergency());
        Assertions.assertEquals(expectedReception.getDoctorId(), actualReception.getDoctorId());
        Assertions.assertEquals(expectedReception.getVitalSign(), actualReception.getVitalSign());
    }

    @Test
    @DisplayName("should throw an exception on null receptionDto")
    void shouldThrowExceptionOnNullReceptionDto() {
        Throwable exception = Assertions.assertThrows(NullPointerException.class, () -> {
            this.receptionService.mapReceptionDtoToReception((ReceptionDto)null);
        });
        Assertions.assertEquals("updateReceptionDto is null", exception.getMessage());
    }
}
