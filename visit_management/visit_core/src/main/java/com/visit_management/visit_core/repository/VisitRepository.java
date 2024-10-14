package com.visit_management.visit_core.repository;

import com.visit_management.visit_core.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit,String> {

   Visit findVisitByReceptionId(String receptionId);
   void deleteByVisitId(String s);
}
