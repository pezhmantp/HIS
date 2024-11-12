package com.pharmacy_management.pharmacy_core.repository;

import com.pharmacy_management.pharmacy_core.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine,String> {
    Medicine findByName(String name);
}
