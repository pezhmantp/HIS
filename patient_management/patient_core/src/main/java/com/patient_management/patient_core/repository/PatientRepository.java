package com.patient_management.patient_core.repository;

import com.patient_management.patient_core.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientRepository extends JpaRepository<Patient,String> {

    Patient findByPatientId(String patientId);
    Patient findByNationalId(String nationalId);
    boolean existsByNationalId(String nationalId);
}
