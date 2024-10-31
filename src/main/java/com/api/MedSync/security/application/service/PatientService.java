package com.api.MedSync.security.application.service;

import com.api.MedSync.security.application.commands.UpdatePatientCarerCommand;
import com.api.MedSync.security.application.commands.UpdatePatientCommand;
import com.api.MedSync.security.domain.model.Carer;
import com.api.MedSync.security.domain.model.Patient;
import com.api.MedSync.security.infrastructure.persistence.repository.PatientRepository;
import com.api.MedSync.security.infrastructure.persistence.repository.UserRepository;
import com.api.MedSync.security.interfaces.dto.CarerResponse;
import com.api.MedSync.security.interfaces.dto.PatientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository carerRepository;
    private final UserRepository userRepository;

    public PatientResponse updatePatientCarer(UpdatePatientCarerCommand command) {
        Patient patient = patientRepository.findById(command.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Recuperar el Carer y hacer un chequeo de tipo para asegurar que es de tipo Carer
        Carer carer = (Carer) userRepository.findById(command.getCarerId())
                .orElseThrow(() -> new RuntimeException("Carer not found"));

        if (!(carer instanceof Carer)) {
            throw new RuntimeException("The specified user is not a Carer");
        }

        // Asociar el cuidador al paciente y viceversa
        patient.setCarer(carer);
        carer.setPatient(patient);

        // Guardar ambos en la base de datos para asegurar la relación bidireccional
        carerRepository.save(carer);
        patient = patientRepository.save(patient);

        return toPatientResponse(patient);
    }

    public PatientResponse updatePatient(Long id, UpdatePatientCommand command) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            patient.setDisease(command.getDisease());
            patient.setWeight(command.getWeight());
            patient.setPhoneNumber(command.getPhoneNumber());
            patient.setProfilePictureUrl(command.getProfilePictureUrl());
            return toPatientResponse(patientRepository.save(patient));
        } else {
            throw new RuntimeException("Patient not found");
        }
    }

    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return toPatientResponse(patient);
    }

    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(this::toPatientResponse)
                .collect(Collectors.toList());
    }

    private PatientResponse toPatientResponse(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .name(patient.getName())
                .lastName(patient.getLastname())
                .phoneNumber(patient.getPhoneNumber())
                .disease(patient.getDisease())
                .weight(patient.getWeight())
                .build();
    }
}
