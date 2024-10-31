package com.api.MedSync.security.interfaces.rest;

import com.api.MedSync.security.application.commands.UpdateCarerCommand;
import com.api.MedSync.security.application.service.CarerService;
import com.api.MedSync.security.interfaces.dto.CarerResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Carer", description = "Carer actions")
@Controller
@RequestMapping("/api/v1/carer")
@RequiredArgsConstructor
public class CarerController {

    private final CarerService carerService;

    @PutMapping("/{id}")
    public ResponseEntity<CarerResponse> updateCarer(@PathVariable Long id, @RequestBody UpdateCarerCommand command) {
        return ResponseEntity.ok(carerService.updateCarer(id, command));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarerResponse> getCarerById(@PathVariable Long id) {
        return ResponseEntity.ok(carerService.getCarerById(id));
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<CarerResponse> getCarerByPhoneNumber(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(carerService.getCarerByPhoneNumber(phoneNumber));
    }

    @GetMapping
    public ResponseEntity<List<CarerResponse>> getAllCarers() {
        return ResponseEntity.ok(carerService.getAllCarers());
    }
}