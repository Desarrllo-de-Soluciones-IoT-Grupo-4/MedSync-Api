package com.api.MedSync.security.application.service;

import com.api.MedSync.security.application.commands.UpdateCarerCommand;
import com.api.MedSync.security.domain.model.Carer;
import com.api.MedSync.security.infrastructure.persistence.repository.CarerRepository;
import com.api.MedSync.security.interfaces.dto.CarerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarerService {

    private final CarerRepository carerRepository;

    public CarerResponse updateCarer(Long id, UpdateCarerCommand command) {
        Optional<Carer> carerOptional = carerRepository.findById(id);
        if (carerOptional.isPresent()) {
            Carer carer = carerOptional.get();
            carer.setPhoneNumber(command.getPhoneNumber());
            carer.setProfilePictureUrl(command.getProfilePictureUrl());
            return toCarerResponse(carerRepository.save(carer));
        } else {
            throw new RuntimeException("Carer not found");
        }
    }

    public CarerResponse getCarerById(Long id) {
        Carer carer = carerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carer not found"));
        return toCarerResponse(carer);
    }

    public CarerResponse getCarerByPhoneNumber(String phoneNumber) {
        Carer carer = carerRepository.findByPhoneNumber(phoneNumber);
        return toCarerResponse(carer);
    }

    public List<CarerResponse> getAllCarers() {
        return carerRepository.findAll()
                .stream()
                .map(this::toCarerResponse)
                .collect(Collectors.toList());
    }

    private CarerResponse toCarerResponse(Carer carer) {
        return CarerResponse.builder()
                .id(carer.getId())
                .name(carer.getName())
                .lastName(carer.getLastname())
                .phoneNumber(carer.getPhoneNumber())
                .profilePictureUrl(carer.getProfilePictureUrl())
                .build();
    }
}