package com.svts.appointmentservice.repository;

import com.svts.appointmentservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient,String> {
    public List<?> findByNameLikeIgnoreCase(String name);
}
