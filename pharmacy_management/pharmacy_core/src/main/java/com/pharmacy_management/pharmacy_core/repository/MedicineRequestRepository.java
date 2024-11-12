package com.pharmacy_management.pharmacy_core.repository;

import com.pharmacy_management.pharmacy_core.model.MedicineRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRequestRepository extends JpaRepository<MedicineRequest,String> {
    MedicineRequest findByMedicineRequestId(String medicineRequestId);
}
