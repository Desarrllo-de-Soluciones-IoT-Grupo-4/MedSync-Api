package com.api.MedSync.security.application.commands;

import com.api.MedSync.security.domain.aggregate.Role;
import lombok.Data;

@Data
public class SignUpCommand {
    private String email;
    private String name;
    private String lastname;
    private String password;
    private Role role;
}
