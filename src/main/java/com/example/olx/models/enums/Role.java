package com.example.olx.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR, ROLE_ASF;

    @Override
    public String getAuthority() {
        return name();
    }
}
