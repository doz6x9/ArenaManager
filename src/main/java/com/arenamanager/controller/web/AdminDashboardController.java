package com.arenamanager.controller.web;

import com.arenamanager.service.TeamService;
import com.arenamanager.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final TournamentService tournamentService;
    private final TeamService teamService;

    public AdminDashboardController(TournamentService tournamentService, TeamService teamService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("tournaments", tournamentService.listTournaments());
        model.addAttribute("teams", teamService.listTeams());
        return "dashboard";
    }
}
