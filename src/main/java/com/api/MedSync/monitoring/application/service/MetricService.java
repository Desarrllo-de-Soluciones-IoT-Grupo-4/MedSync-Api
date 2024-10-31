package com.api.MedSync.monitoring.application.service;

import com.api.MedSync.monitoring.domain.model.Metric;
import com.api.MedSync.monitoring.infrastructure.persistence.MetricRepository;
import com.api.MedSync.security.domain.model.Patient;
import com.api.MedSync.security.infrastructure.persistence.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MetricService {

    private final MetricRepository metricRepository;
    private final PatientRepository patientRepository;

    public Metric getMetricById(Long id) {
        return metricRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Metric not found"));
    }

    public Metric getMetricByDate(Long patientId, LocalDate date) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return metricRepository.findByPatientAndDate(patient, date)
                .orElseThrow(() -> new RuntimeException("Metric not found for the given date"));
    }

    public List<Metric> getAllMetrics(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return metricRepository.findAllByPatient(patient);
    }
}