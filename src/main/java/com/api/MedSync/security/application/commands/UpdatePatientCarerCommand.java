package com.api.MedSync.security.application.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePatientCarerCommand {
    private Long patientId;
    private Long carerId;
}