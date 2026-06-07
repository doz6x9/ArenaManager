package com.arenamanager.controller.rest;

import com.arenamanager.dto.LoginRequest;
import com.arenamanager.dto.RegistrationRequestDto;
import com.arenamanager.dto.TokenResponse;
import com.arenamanager.security.JwtService;
import com.arenamanager.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController extends AbstractRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RegistrationService registrationService;
    private final UserDetailsService userDetailsService;

    public AuthRestController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            RegistrationService registrationService,
            UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.registrationService = registrationService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected String resourceName() {
        return "auth";
    }

    @PostMapping("/token")
    public TokenResponse token(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new TokenResponse(jwtService.generateToken(userDetails), "Bearer");
    }

    @PostMapping("/register")
    public TokenResponse register(@Valid @RequestBody RegistrationRequestDto request) {
        registrationService.registerPlayer(request);
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        return new TokenResponse(jwtService.generateToken(userDetails), "Bearer");
    }
}
