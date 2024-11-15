package com.api.MedSync.alert.application.service;

import com.api.MedSync.alert.domain.model.Alert;
import com.api.MedSync.alert.infrastructure.persistence.AlertRepository;
import com.api.MedSync.monitoring.infrastructure.persistence.HeartRateRepository;
import com.api.MedSync.security.domain.model.Carer;
import com.api.MedSync.security.domain.model.Patient;
import com.api.MedSync.security.infrastructure.persistence.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.api.MedSync.alert.domain.model.Type;


import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final HeartRateRepository heartRateRepository;
    private final PatientRepository patientRepository;
    private final AlertRepository alertRepository;
    private final SimpMessagingTemplate messagingTemplate;

    private static final double MIN_HEARTRATE = 45.0;
    private static final double MAX_HEARTRATE = 100.0;

    @Transactional
    public Alert checkAndGenerateAlert(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeMinutesAgo = now.minusMinutes(3);

        List<Double> heartRates = heartRateRepository.findAllByPatientAndDateBetween(patient, threeMinutesAgo, now)
                .stream()
                .map(hr -> hr.getValue())
                .toList();

        if (heartRates.isEmpty()) {
            return null;
        }

        DoubleSummaryStatistics stats = heartRates.stream()
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();

        double average = stats.getAverage();

        if (average < MIN_HEARTRATE || average > MAX_HEARTRATE) {
            Carer carer = patient.getCarer();
            if (carer == null) {
                throw new RuntimeException("Carer not assigned to patient");
            }

            Alert alert = new Alert();
            alert.setCarer(carer);
            alert.setDate(LocalDateTime.now());
            alert.setMessage("Heart rate abnormal: " + average + " bpm");
            alert.setTypeAlert(average < MIN_HEARTRATE ? Type.CRITIC : Type.WARNING);
            alert.setInstructions("Please check on the patient immediately.");

            Alert savedAlert = alertRepository.save(alert);

            // Notificar al cuidador en tiempo real
            messagingTemplate.convertAndSend("/topic/alerts/" + carer.getId(), savedAlert);

            return savedAlert;
        }

        return null;
    }

    public List<Alert> getRecentAlerts(Long carerId) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        return alertRepository.findByCarerIdAndDateAfter(carerId, oneHourAgo);
    }
}