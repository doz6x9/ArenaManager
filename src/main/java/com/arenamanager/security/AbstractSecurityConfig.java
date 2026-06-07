package com.arenamanager.security;

import org.springframework.security.core.Authentication;

public abstract class AbstractSecurityConfig {

    protected abstract String securityName();

    protected boolean hasAuthority(Authentication authentication, String authority) {
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
    }
}
