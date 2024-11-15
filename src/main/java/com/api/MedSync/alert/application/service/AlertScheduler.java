package com.api.MedSync.alert.application.service;

import com.api.MedSync.security.infrastructure.persistence.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlertScheduler {

    private final AlertService alertService;
    private final PatientRepository patientRepository;

    @Scheduled(fixedRate = 60000) // Cada 60 segundos
    public void checkAllPatientsHeartRates() {
        patientRepository.findAll().forEach(patient -> {
            try {
                alertService.checkAndGenerateAlert(patient.getId());
            } catch (Exception e) {
                System.err.println("Error processing alerts for patient ID " + patient.getId() + ": " + e.getMessage());
            }
        });
    }
}