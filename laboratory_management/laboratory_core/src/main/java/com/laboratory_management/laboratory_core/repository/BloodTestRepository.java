package com.laboratory_management.laboratory_core.repository;

import com.laboratory_management.laboratory_core.model.BloodTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodTestRepository extends JpaRepository<BloodTest,String> {
}
