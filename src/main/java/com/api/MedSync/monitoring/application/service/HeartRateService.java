package com.api.MedSync.monitoring.application.service;

import com.api.MedSync.monitoring.domain.model.HeartRate;
import com.api.MedSync.monitoring.domain.model.Metric;
import com.api.MedSync.monitoring.infrastructure.persistence.HeartRateRepository;
import com.api.MedSync.monitoring.infrastructure.persistence.MetricRepository;
import com.api.MedSync.security.domain.model.Patient;
import com.api.MedSync.security.infrastructure.persistence.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HeartRateService {

    private final HeartRateRepository heartRateRepository;
    private final MetricRepository metricRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public HeartRate saveHeartRate(Long patientId, Double value) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        HeartRate heartRate = new HeartRate();
        heartRate.setPatient(patient);
        heartRate.setValue(value);
        heartRate.setDate(LocalDateTime.now());

        HeartRate savedHeartRate = heartRateRepository.save(heartRate);
        updateMetric(patient, savedHeartRate.getDate().toLocalDate());

        return savedHeartRate;
    }

    @Transactional
    public void deleteHeartRate(Long id) {
        heartRateRepository.deleteById(id);
    }

    public List<HeartRate> getAllHeartRates() {
        return heartRateRepository.findAll();
    }

    public HeartRate getHeartRateById(Long id) {
        return heartRateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HeartRate not found"));
    }

    private void updateMetric(Patient patient, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<HeartRate> heartRates = heartRateRepository.findAllByPatientAndDateBetween(patient, startOfDay, endOfDay);
        DoubleSummaryStatistics stats = heartRates.stream()
                .mapToDouble(HeartRate::getValue)
                .summaryStatistics();

        Metric metric = metricRepository.findByPatientAndDate(patient, date)
                .orElse(new Metric());

        metric.setPatient(patient);
        metric.setDate(date);
        metric.setAverage(stats.getAverage());
        metric.setMaxFrequency(stats.getMax());
        metric.setMinFrequency(stats.getMin());

        metricRepository.save(metric);
    }
}