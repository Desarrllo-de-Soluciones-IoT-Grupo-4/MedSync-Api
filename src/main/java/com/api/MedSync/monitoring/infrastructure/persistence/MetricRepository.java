package com.api.MedSync.monitoring.infrastructure.persistence;

import com.api.MedSync.monitoring.domain.model.Metric;
import com.api.MedSync.security.domain.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MetricRepository extends JpaRepository<Metric, Long> {
    Optional<Metric> findByPatientAndDate(Patient patient, LocalDate date);
    List<Metric> findAllByPatient(Patient patient);
}