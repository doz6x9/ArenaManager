package com.arenamanager.controller.web;

import com.arenamanager.AbstractLayerComponent;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public abstract class AbstractWebController implements AbstractLayerComponent {

    @Override
    public String componentName() {
        return "web-controller";
    }

    protected boolean isSignedIn(Authentication authentication) {
        return authentication != null
                && authentication.isAuthenticated()
                && !hasRole(authentication, "ROLE_ANONYMOUS");
    }

    protected boolean hasRole(Authentication authentication, String role) {
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }

    protected void addHomeNavigation(Model model, Authentication authentication) {
        model.addAttribute("homePath", dashboardPath(authentication));
        model.addAttribute("homeLabel", dashboardLabel(authentication));
    }

    protected String dashboardPath(Authentication authentication) {
        if (hasRole(authentication, "ROLE_ORGANIZER")) {
            return "/admin/dashboard";
        }
        if (hasRole(authentication, "ROLE_PLAYER")) {
            return "/player/dashboard";
        }
        if (hasRole(authentication, "ROLE_CAPTAIN")) {
            return "/captain/dashboard";
        }
        return "/login";
    }

    protected String dashboardLabel(Authentication authentication) {
        if (hasRole(authentication, "ROLE_ORGANIZER")) {
            return "Organizer dashboard";
        }
        if (hasRole(authentication, "ROLE_PLAYER")) {
            return "Player dashboard";
        }
        if (hasRole(authentication, "ROLE_CAPTAIN")) {
            return "Captain dashboard";
        }
        return "Login";
    }
}
