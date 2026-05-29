package com.arenamanager.controller.web;

import com.arenamanager.service.ApiRouteCatalogService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiRouteWebController {

    private final ApiRouteCatalogService apiRouteCatalogService;

    public ApiRouteWebController(ApiRouteCatalogService apiRouteCatalogService) {
        this.apiRouteCatalogService = apiRouteCatalogService;
    }

    @GetMapping("/api-routes")
    public String routeDirectory(Authentication authentication) {
        if (hasRole(authentication, "ROLE_ORGANIZER")) {
            return "redirect:/admin/api-routes";
        }
        if (hasRole(authentication, "ROLE_PLAYER")) {
            return "redirect:/player/api-routes";
        }
        return "redirect:/captain/api-routes";
    }

    @GetMapping("/admin/api-routes")
    public String adminRoutes(Model model) {
        addRouteModel(model, "Organizer API Routes", "/admin/dashboard", "Organizer dashboard");
        return "api-routes";
    }

    @GetMapping("/captain/api-routes")
    public String captainRoutes(Model model) {
        addRouteModel(model, "Captain API Routes", "/captain/dashboard", "Captain dashboard");
        return "api-routes";
    }

    @GetMapping("/player/api-routes")
    public String playerRoutes(Model model) {
        addRouteModel(model, "Player API Routes", "/player/dashboard", "Player dashboard");
        return "api-routes";
    }

    private void addRouteModel(Model model, String title, String homePath, String homeLabel) {
        model.addAttribute("title", title);
        model.addAttribute("homePath", homePath);
        model.addAttribute("homeLabel", homeLabel);
        model.addAttribute("routes", apiRouteCatalogService.listRoutes());
        model.addAttribute("captainRouteCount", apiRouteCatalogService.captainRoutes().size());
        model.addAttribute("routeCount", apiRouteCatalogService.listRoutes().size());
    }

    private boolean hasRole(Authentication authentication, String role) {
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }
}
