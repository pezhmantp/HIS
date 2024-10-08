package com.laboratory_management.laboratory_core.repository;

import com.laboratory_management.laboratory_core.model.UrinalysisTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrinalysisTestRepository extends JpaRepository<UrinalysisTest,String> {
    List<UrinalysisTest> findAllByVisitId(String visitId);
}
