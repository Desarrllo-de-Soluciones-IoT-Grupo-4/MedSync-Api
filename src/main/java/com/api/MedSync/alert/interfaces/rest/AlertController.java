package com.api.MedSync.alert.interfaces.rest;

import com.api.MedSync.alert.application.service.AlertService;
import com.api.MedSync.alert.domain.model.Alert;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Alerts", description = "Alerts actions")
@Controller
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping("/recent")
    public ResponseEntity<List<Alert>> getRecentAlerts(@RequestParam Long carerId) {
        List<Alert> alerts = alertService.getRecentAlerts(carerId);
        return ResponseEntity.ok(alerts);
    }
}