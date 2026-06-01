package com.arenamanager.controller.web;

import com.arenamanager.dto.RegistrationRequestDto;
import com.arenamanager.exception.BusinessRuleException;
import com.arenamanager.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationWebController {

    private final RegistrationService registrationService;

    public RegistrationWebController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        if (!model.containsAttribute("registration")) {
            model.addAttribute("registration", new RegistrationRequestDto());
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registration") RegistrationRequestDto registration,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            registrationService.registerPlayer(registration);
            redirectAttributes.addFlashAttribute("success", "Account created. Sign in with your new player login.");
            return "redirect:/login?registered";
        } catch (BusinessRuleException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
