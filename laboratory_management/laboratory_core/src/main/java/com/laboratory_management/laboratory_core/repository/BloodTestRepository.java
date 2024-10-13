package com.laboratory_management.laboratory_core.repository;

import com.laboratory_management.laboratory_core.model.BloodTest;
import com.laboratory_management.laboratory_core.model.UrinalysisTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodTestRepository extends JpaRepository<BloodTest,String> {
    List<BloodTest> findAllByVisitId(String visitId);
    @Query("SELECT t FROM BloodTest t WHERE t.status = 'in progress'")
    List<BloodTest> findAllOpenTests();
    BloodTest findTestByTestId(String testId);
}
