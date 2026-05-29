package com.arenamanager.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "arena.demo-data=false")
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void organizerLoginRedirectsToDashboard() throws Exception {
        mockMvc.perform(formLogin().user("organizer").password("password"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin/dashboard"));
    }

    @Test
    void captainLoginRedirectsToCaptainDashboard() throws Exception {
        mockMvc.perform(formLogin().user("captain").password("password"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/captain/dashboard"));
    }

    @Test
    void captainCanOpenCaptainDashboard() throws Exception {
        mockMvc.perform(get("/captain/dashboard").with(user("captain").roles("CAPTAIN")))
                .andExpect(status().isOk());
    }

    @Test
    void captainCannotPostScoreUpdates() throws Exception {
        mockMvc.perform(post("/matches/score")
                        .with(user("captain").roles("CAPTAIN"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
}
