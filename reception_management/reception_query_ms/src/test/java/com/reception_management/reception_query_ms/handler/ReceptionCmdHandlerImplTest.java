package com.reception_management.reception_query_ms.handler;

import com.reception_management.reception_core.event.NewReceptionCreatedEvent;
import com.reception_management.reception_core.event.ReceptionRemovedEvent;
import com.reception_management.reception_core.event.ReceptionUpdatedEvent;
import com.reception_management.reception_core.model.Reception;
import com.reception_management.reception_core.model.VitalSign;
import com.reception_management.reception_core.repository.ReceptionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReceptionCmdHandlerImplTest {
    @InjectMocks
    private ReceptionCmdHandlerImpl receptionCmdHandler;
    @Mock
    private ReceptionRepository receptionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Captor
    private ArgumentCaptor<Reception> receptionArgumentCaptor;
    @Test
    @DisplayName("should save the new reception on receiving NewReceptionCreatedEvent")
    void onNewReceptionCreatedEvent() {
        Reception reception=new Reception();
        reception.setReceptionId("receptionId");
        reception.setPatientId("123");
        long ms=System.currentTimeMillis();
        Date date=new Date(ms);
        reception.setDateOfReception(date);
        reception.setEmergency(false);
        reception.setComment("comment");
        reception.setDoctorId("doctorId");
        reception.setVitalSign(new VitalSign(1L,13.5,7.5,35.5,93.2));
        NewReceptionCreatedEvent event=new NewReceptionCreatedEvent("receptionId",reception);
        receptionCmdHandler.onNewReceptionCreatedEvent(event);

        Mockito.verify(receptionRepository,Mockito.times(1)).save(receptionArgumentCaptor.capture());

        Assertions.assertEquals("receptionId",receptionArgumentCaptor.getValue().getReceptionId());
        Assertions.assertEquals("123",receptionArgumentCaptor.getValue().getPatientId());        Assertions.assertEquals("receptionId",receptionArgumentCaptor.getValue().getReceptionId());
        Assertions.assertEquals(reception.getDateOfReception(),receptionArgumentCaptor.getValue().getDateOfReception());
        Assertions.assertEquals("comment",receptionArgumentCaptor.getValue().getComment());
        Assertions.assertEquals(false,receptionArgumentCaptor.getValue().getEmergency());
        Assertions.assertEquals("doctorId",receptionArgumentCaptor.getValue().getDoctorId());
        Assertions.assertEquals(reception.getVitalSign(),receptionArgumentCaptor.getValue().getVitalSign());
    }


    @Test
    @DisplayName("should save the reception on receiving ReceptionUpdatedEvent")
    void onReceptionUpdatedEvent() {
        Reception reception=new Reception();
        reception.setReceptionId("receptionId");
        reception.setPatientId("123");
        long ms=System.currentTimeMillis();
        Date date=new Date(ms);
        reception.setDateOfReception(date);
        reception.setEmergency(false);
        reception.setComment("comment");
        reception.setDoctorId("doctorId");
        reception.setVitalSign(new VitalSign(1L,13.5,7.5,35.5,93.2));
        ReceptionUpdatedEvent event=new ReceptionUpdatedEvent("receptionId",reception);
        receptionCmdHandler.onReceptionUpdatedEvent(event);

        Mockito.verify(receptionRepository,Mockito.times(1)).save(receptionArgumentCaptor.capture());


        Assertions.assertEquals("receptionId",receptionArgumentCaptor.getValue().getReceptionId());
        Assertions.assertEquals("123",receptionArgumentCaptor.getValue().getPatientId());        Assertions.assertEquals("receptionId",receptionArgumentCaptor.getValue().getReceptionId());
        Assertions.assertEquals(reception.getDateOfReception(),receptionArgumentCaptor.getValue().getDateOfReception());
        Assertions.assertEquals("comment",receptionArgumentCaptor.getValue().getComment());
        Assertions.assertEquals(false,receptionArgumentCaptor.getValue().getEmergency());
        Assertions.assertEquals("doctorId",receptionArgumentCaptor.getValue().getDoctorId());
        Assertions.assertEquals(reception.getVitalSign(),receptionArgumentCaptor.getValue().getVitalSign());
    }

    @Test
    @DisplayName("should delete the patient on receiving PatientRemovedEvent")
    void onPatientRemovedEvent() {
        ReceptionRemovedEvent event=new ReceptionRemovedEvent("receptionId");
        Mockito.doNothing().when(receptionRepository).deleteById(event.getId());
        receptionCmdHandler.onReceptionRemovedEvent(event);

        Mockito.verify(receptionRepository,Mockito.times(1)).deleteById("receptionId");
    }
}