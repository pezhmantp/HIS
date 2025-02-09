package com.reception_management.reception_core.repository;

import com.reception_management.reception_core.model.Reception;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceptionRepository extends JpaRepository<Reception, String> {
    Reception findByReceptionId(String receptionId);
    @Query("SELECT r FROM Reception r WHERE r.visitStatus != 'visited' AND r.receptionStatus !='completed' AND r.doctorId =?1 ORDER BY r.emergency DESC")
    List<Reception> findByDoctorId(String doctorId);
    @Query("SELECT r FROM Reception r WHERE r.receptionStatus ='open' AND r.patientId = ?1 ")
    Reception findOpenReceptionByPatientId(String patientId);
    Reception findByPatientId(String patientId);

    @Query("SELECT r FROM Reception r WHERE r.receptionStatus = ?1")
    List<Reception> findAll(String receptionStatus, Sort sort);
}

