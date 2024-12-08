package com.api_gateway.api_gateway_service;

import com.api_gateway.api_gateway_service.response.FallbackResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    private static final Logger LOG = LoggerFactory.getLogger(FallbackController.class);

    @GetMapping("/patient-query-fallback")
    public ResponseEntity<?> patientQueryServiceFallback() {
        LOG.info("Message from fallback method of patient_query_ms");
        return ResponseEntity.ok(new FallbackResponse(null,"fallback"));
    }

    @PostMapping("/patient-cmd-fallback")
    public ResponseEntity<?> patientCmdServiceFallback() {
        LOG.info("Message from fallback method of patient_cmd_ms");
        return ResponseEntity.ok(new FallbackResponse(null,"fallback"));
    }
    @GetMapping("/reception-query-fallback")
    public ResponseEntity<?> receptionQueryServiceFallback() {
        LOG.info("Message from fallback method of reception_query_ms");
        return ResponseEntity.ok(new FallbackResponse(null,"fallback"));
    }
    @PostMapping("/reception-cmd-fallback")
    public ResponseEntity<?> receptionCmdServiceFallback() {
        LOG.info("Message from fallback method of reception_cmd_ms");
        return ResponseEntity.ok(new FallbackResponse(null,"fallback"));
    }
    @PatchMapping("/reception-cmd-fallback")
    public ResponseEntity<?> receptionCmdServiceFallbackPatch() {
        LOG.info("Message from fallback method of reception_cmd_ms");
        return ResponseEntity.ok(new FallbackResponse(null,"fallback"));
    }
    @DeleteMapping("/reception-cmd-fallback")
    public ResponseEntity<?> receptionCmdServiceFallbackDelete() {
        LOG.info("Message from fallback method of reception_cmd_ms");
        return ResponseEntity.ok(new FallbackResponse(null,"fallback"));
    }
    @GetMapping("/pharmacy-query-fallback")
    public ResponseEntity<?> medicineRequestQueryFallback() {
        LOG.info("Message from fallback method of pharmacy_query_ms");
        return ResponseEntity.ok(new FallbackResponse(null,"fallback"));
    }
    @PostMapping("/pharmacy-cmd-fallback")
    public ResponseEntity<?> pharmacyCmdFallbackPost() {
        LOG.info("Message from fallback method of pharmacy_query_ms");
        return ResponseEntity.ok(new FallbackResponse(null,"fallback"));
    }
    @PostMapping("/lab-cmd-fallback")
    public ResponseEntity<?> labCmdFallbackPost() {
        LOG.info("Message from fallback method of lab_cmd_ms");
        return ResponseEntity.ok(new FallbackResponse(null,"fallback"));
    }
    @DeleteMapping("/lab-cmd-fallback")
    public ResponseEntity<?> labCmdFallbackDelete() {
        LOG.info("Message from fallback method of lab_cmd_ms");
        return ResponseEntity.ok(new FallbackResponse(null,"fallback"));
    }
    @GetMapping("/lab-query-fallback")
    public ResponseEntity<?> labQueryFallbackGet() {
        LOG.info("Message from fallback method of lab_query_ms");
        return ResponseEntity.ok(new FallbackResponse(null,"fallback"));
    }
    @PostMapping("/visit-cmd-fallback")
    public ResponseEntity<?> visitCmdFallbackPost() {
        LOG.info("Message from fallback method of visit_cmd_ms");
        return ResponseEntity.ok(new FallbackResponse(null,"fallback"));
    }
    @DeleteMapping("/visit-cmd-fallback")
    public ResponseEntity<?> visitCmdFallbackDelete() {
        LOG.info("Message from fallback method of visit_cmd_ms");
        return ResponseEntity.ok(new FallbackResponse(null,"fallback"));
    }
    @GetMapping("/visit-query-fallback")
    public ResponseEntity<?> visitQueryFallbackGet() {
        LOG.info("Message from fallback method of visit_query_ms");
        return ResponseEntity.ok(new FallbackResponse(null,"fallback"));
    }
}
