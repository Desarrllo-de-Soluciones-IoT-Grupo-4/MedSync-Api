package com.api.MedSync.security.interfaces.rest;

import com.api.MedSync.security.application.commands.UpdatePatientCarerCommand;
import com.api.MedSync.security.application.commands.UpdatePatientCommand;
import com.api.MedSync.security.application.service.PatientService;
import com.api.MedSync.security.interfaces.dto.PatientResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Patients", description = "Patients actions")
@Controller
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long id, @RequestBody UpdatePatientCommand command) {
        return ResponseEntity.ok(patientService.updatePatient(id, command));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }
    @PutMapping("/{id}/carer")
    public ResponseEntity<PatientResponse> updatePatientCarer(@PathVariable Long id, @RequestBody UpdatePatientCarerCommand command) {
        command.setPatientId(id); // Establecer el ID del paciente en el comando
        return ResponseEntity.ok(patientService.updatePatientCarer(command));
    }
}
