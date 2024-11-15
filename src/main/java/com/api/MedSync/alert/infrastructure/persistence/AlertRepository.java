package com.api.MedSync.alert.infrastructure.persistence;

import com.api.MedSync.alert.domain.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByCarerIdAndDateAfter(Long carerId, LocalDateTime date);
}