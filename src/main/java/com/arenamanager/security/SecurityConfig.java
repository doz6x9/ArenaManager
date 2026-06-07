package com.arenamanager.security;

import com.arenamanager.repository.UserAccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends AbstractSecurityConfig {

    @Override
    protected String securityName() {
        return "spring-security";
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/h2-console/**"))
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/register", "/error", "/css/**", "/h2-console/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/tournaments", "/api/tournaments/**",
                                "/api/teams", "/api/teams/**",
                                "/api/players", "/api/players/**",
                                "/api/matches", "/api/matches/**").hasAnyRole("ORGANIZER", "CAPTAIN", "PLAYER")
                        .requestMatchers("/api/reports/**").hasRole("ORGANIZER")
                        .requestMatchers("/api/**").hasRole("ORGANIZER")
                        .requestMatchers("/admin/**").hasRole("ORGANIZER")
                        .requestMatchers("/captain/**").hasRole("CAPTAIN")
                        .requestMatchers("/player/**").hasRole("PLAYER")
                        .requestMatchers("/tournaments/*/bracket/generate", "/matches/**").hasRole("ORGANIZER")
                        .requestMatchers(HttpMethod.GET, "/tournaments", "/tournaments/*/bracket").hasAnyRole("ORGANIZER", "CAPTAIN", "PLAYER")

                        .requestMatchers("/tournaments/**").hasRole("ORGANIZER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            boolean isOrganizer = hasAuthority(authentication, "ROLE_ORGANIZER");
                            boolean isCaptain = hasAuthority(authentication, "ROLE_CAPTAIN");
                            boolean isPlayer = hasAuthority(authentication, "ROLE_PLAYER");

                            if (isOrganizer) {
                                SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
                                if (savedRequest == null) {
                                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                                } else {
                                    response.sendRedirect(savedRequest.getRedirectUrl());
                                }
                            } else if (isCaptain) {
                                response.sendRedirect(request.getContextPath() + "/captain/dashboard");
                            } else if (isPlayer) {
                                response.sendRedirect(request.getContextPath() + "/player/dashboard");

                            } else {
                                response.sendRedirect(request.getContextPath() + "/");
                            }
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder, UserAccountRepository userAccountRepository) {
        UserDetails organizer = User.withUsername("organizer")
                .password(passwordEncoder.encode("password"))
                .roles("ORGANIZER", "CAPTAIN")
                .build();
        UserDetails captain = User.withUsername("captain")
                .password(passwordEncoder.encode("password"))
                .roles("CAPTAIN")
                .build();
       //return new InMemoryUserDetailsManager(organizer, captain);
        UserDetails player = User.withUsername("player")
                .password(passwordEncoder.encode("password"))
                .roles("PLAYER")
                .build();
        Map<String, UserDetails> demoUsers = Map.of(
                organizer.getUsername(), organizer,
                captain.getUsername(), captain,
                player.getUsername(), player
        );
        return username -> userAccountRepository.findByUsername(username)
                .map(account -> User.withUsername(account.getUsername())
                        .password(account.getPasswordHash())
                        .roles(account.getRole())
                        .build())
                .orElseGet(() -> {
                    UserDetails demoUser = demoUsers.get(username);
                    if (demoUser != null) {
                        return demoUser;
                    }
                    throw new UsernameNotFoundException("User not found: " + username);
                });
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
