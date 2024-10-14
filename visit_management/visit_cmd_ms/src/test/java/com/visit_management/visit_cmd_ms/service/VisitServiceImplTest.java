package com.visit_management.visit_cmd_ms.service;

import com.visit_management.visit_cmd_ms.dto.VisitStatusDto;
import com.visit_management.visit_cmd_ms.kafka.config.KafkaConfigData;
import com.visit_management.visit_core.repository.VisitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import static org.mockito.Mockito.mock;

class VisitServiceImplTest {
    @InjectMocks
    @Spy
    private VisitServiceImpl visitServiceImpl;

    public VisitServiceImplTest() {

    }


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should generate new random visitId with length of 12")
    void shouldGenerateVisitId() {
        String visitId = this.visitServiceImpl.generateVisitId();
        Assertions.assertNotNull(visitId);
        Assertions.assertTrue(visitId.length() == 12);
    }


}