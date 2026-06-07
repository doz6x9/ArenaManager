package com.arenamanager.controller.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends AbstractWebController {

    @GetMapping("/")
    public String home() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isSignedIn(authentication)) {
            return "redirect:" + dashboardPath(authentication);
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
