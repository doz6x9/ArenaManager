package com.arenamanager.security;

import com.arenamanager.AbstractLayerComponent;
import org.springframework.security.core.Authentication;

public abstract class AbstractSecurityConfig implements AbstractLayerComponent {

    protected abstract String securityName();

    @Override
    public String componentName() {
        return "security:" + securityName();
    }

    protected boolean hasAuthority(Authentication authentication, String authority) {
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
    }
}
