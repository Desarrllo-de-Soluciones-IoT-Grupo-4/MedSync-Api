package com.api.MedSync.monitoring.interfaces.rest;

import com.api.MedSync.monitoring.application.service.HeartRateService;
import com.api.MedSync.monitoring.domain.model.HeartRate;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "HeartRates", description = "HeartRates actions")
@Controller
@RestController
@RequestMapping("/api/v1/heartrates")
@RequiredArgsConstructor
public class HeartRateController {

    private final HeartRateService heartRateService;

    @PostMapping
    public ResponseEntity<HeartRate> saveHeartRate(@RequestParam Long patientId, @RequestParam Double value) {
        HeartRate savedHeartRate = heartRateService.saveHeartRate(patientId, value);
        return ResponseEntity.ok(savedHeartRate);
    }

    @GetMapping
    public ResponseEntity<List<HeartRate>> getAllHeartRates() {
        List<HeartRate> heartRates = heartRateService.getAllHeartRates();
        return ResponseEntity.ok(heartRates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeartRate> getHeartRateById(@PathVariable Long id) {
        HeartRate heartRate = heartRateService.getHeartRateById(id);
        return ResponseEntity.ok(heartRate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHeartRate(@PathVariable Long id) {
        heartRateService.deleteHeartRate(id);
        return ResponseEntity.noContent().build();
    }
}