package com.api.MedSync.monitoring.interfaces.rest;

import com.api.MedSync.monitoring.application.service.MetricService;
import com.api.MedSync.monitoring.domain.model.Metric;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Metrics", description = "Metrics actions")
@Controller
@RestController
@RequestMapping("/api/v1/metrics")
@RequiredArgsConstructor
public class MetricController {

    private final MetricService metricService;

    @GetMapping("/{id}")
    public ResponseEntity<Metric> getMetricById(@PathVariable Long id) {
        return ResponseEntity.ok(metricService.getMetricById(id));
    }

    @GetMapping("/date")
    public ResponseEntity<Metric> getMetricByDate(@RequestParam Long patientId, @RequestParam LocalDate date) {
        return ResponseEntity.ok(metricService.getMetricByDate(patientId, date));
    }

    @GetMapping
    public ResponseEntity<List<Metric>> getAllMetrics(@RequestParam Long patientId) {
        return ResponseEntity.ok(metricService.getAllMetrics(patientId));
    }
}