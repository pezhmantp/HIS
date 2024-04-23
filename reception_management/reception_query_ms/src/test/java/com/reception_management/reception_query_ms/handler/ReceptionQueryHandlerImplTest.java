package com.reception_management.reception_query_ms.handler;

import com.reception_management.reception_core.model.Reception;
import com.reception_management.reception_core.model.VitalSign;
import com.reception_management.reception_core.queries.FindReceptionByReceptionIdQuery;
import com.reception_management.reception_core.repository.ReceptionRepository;
import com.reception_management.reception_core.responeObj.ReceptionResponseFromQuery;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReceptionQueryHandlerImplTest {
    @InjectMocks
    private ReceptionQueryHandlerImpl receptionQueryHandler;
    @Mock
    private ReceptionRepository receptionRepository;
    @Mock
    private DozerBeanMapper mapper;
    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("Should find reception by receptionId and return reception response")
    void shouldFindReceptionByReceptionId() {

        Reception actualReception=new Reception();
        actualReception.setReceptionId("receptionId");
        actualReception.setPatientId("123");
        long ms=System.currentTimeMillis();
        Date date=new Date(ms);
        actualReception.setDateOfReception(date);
        actualReception.setEmergency(false);
        actualReception.setComment("comment");
        actualReception.setDoctorId("doctorId");
        actualReception.setVitalSign(new VitalSign(1L,13.5,7.5,35.5,93.2));
        FindReceptionByReceptionIdQuery query=new FindReceptionByReceptionIdQuery("receptionId");
        Mockito.when(receptionRepository.findByReceptionId(query.getReceptionId())).thenReturn(actualReception);

        ReceptionResponseFromQuery expectedPatient= receptionQueryHandler.findReceptionByReceptionId(query);

        Assertions.assertNotNull(expectedPatient.getReception());
        Assertions.assertEquals("reception found",expectedPatient.getMessage());
        Assertions.assertEquals(expectedPatient.getReception().getReceptionId(),actualReception.getReceptionId());
        Assertions.assertEquals(expectedPatient.getReception().getPatientId(),actualReception.getPatientId());
        Assertions.assertEquals(expectedPatient.getReception().getDateOfReception(),actualReception.getDateOfReception());
        Assertions.assertEquals(expectedPatient.getReception().getComment(),actualReception.getComment());
        Assertions.assertEquals(expectedPatient.getReception().getEmergency(),actualReception.getEmergency());
        Assertions.assertEquals(expectedPatient.getReception().getDoctorId(),actualReception.getDoctorId());
        Assertions.assertEquals(expectedPatient.getReception().getVitalSign(),actualReception.getVitalSign());
    }
    @Test
    @DisplayName("Should not find any reception by the given receptionId and return reception response with null value for reception")
    void shouldNotFindReceptionByReceptionId() {
        Reception actualReception=new Reception();
        actualReception.setReceptionId("receptionId");
        actualReception.setPatientId("123");
        long ms=System.currentTimeMillis();
        Date date=new Date(ms);
        actualReception.setDateOfReception(date);
        actualReception.setEmergency(false);
        actualReception.setComment("comment");
        actualReception.setDoctorId("doctorId");
        actualReception.setVitalSign(new VitalSign(1L,13.5,7.5,35.5,93.2));
        FindReceptionByReceptionIdQuery query=new FindReceptionByReceptionIdQuery("incorrectReceptionId");
        Mockito.when(receptionRepository.findByReceptionId("receptionId")).thenReturn(actualReception);

        ReceptionResponseFromQuery expectedReception= receptionQueryHandler.findReceptionByReceptionId(query);

        Assertions.assertNull(expectedReception.getReception());
        Assertions.assertEquals("reception not found",expectedReception.getMessage());
    }
}