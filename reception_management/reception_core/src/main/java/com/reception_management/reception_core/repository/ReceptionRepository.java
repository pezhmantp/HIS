package com.reception_management.reception_core.repository;

import com.reception_management.reception_core.model.Reception;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptionRepository extends JpaRepository<Reception, String> {
    Reception findByReceptionId(String receptionId);

    Reception findByPatientId(String patientId);
}

