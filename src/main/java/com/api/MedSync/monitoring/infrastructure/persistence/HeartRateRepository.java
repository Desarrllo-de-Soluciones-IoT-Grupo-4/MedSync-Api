package com.api.MedSync.monitoring.infrastructure.persistence;

import com.api.MedSync.monitoring.domain.model.HeartRate;
import com.api.MedSync.security.domain.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HeartRateRepository extends JpaRepository<HeartRate,Long> {
    List<HeartRate> findAllByPatientAndDateBetween(Patient patient, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
