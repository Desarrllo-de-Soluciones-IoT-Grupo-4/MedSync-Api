package com.api.MedSync.security.interfaces.mapper;

import com.api.MedSync.security.domain.aggregate.Role;
import com.api.MedSync.security.domain.aggregate.User;

import java.util.Map;

public class OAuth2ToUserMapper {
    public static User map(Map<String, Object> attributes) {
        return User.builder()
                .email((String) attributes.get("email"))
                .name((String) attributes.get("given_name") + " " + (String) attributes.get("family_name"))
                .password(null)
                .role(Role.PATIENT)
                .build();
    }
}