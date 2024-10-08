package com.laboratory_management.laboratory_core.repository;

import com.laboratory_management.laboratory_core.model.UrinalysisTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrinalysisTestRepository extends JpaRepository<UrinalysisTest,String> {
}
