package com.api.MedSync.security.infrastructure.persistence.repository;

import com.api.MedSync.security.domain.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {



}
