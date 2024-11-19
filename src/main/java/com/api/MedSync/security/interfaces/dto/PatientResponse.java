package com.api.MedSync.security.interfaces.dto;

import com.api.MedSync.security.domain.model.Carer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
    private Long id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String disease;
    private Double weight;
    private String profilePictureUrl;
}
