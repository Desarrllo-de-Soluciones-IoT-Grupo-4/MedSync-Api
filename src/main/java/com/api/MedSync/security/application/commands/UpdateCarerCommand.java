package com.api.MedSync.security.application.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarerCommand {
    private String profilePictureUrl;
    private String phoneNumber;
}
