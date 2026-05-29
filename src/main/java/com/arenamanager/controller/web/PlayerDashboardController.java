package com.arenamanager.controller.web;

import com.arenamanager.service.ApiRouteCatalogService;
import com.arenamanager.service.ReportingService;
import com.arenamanager.service.TeamService;
import com.arenamanager.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/player")
public class PlayerDashboardController {

    private final TournamentService tournamentService;
    private final TeamService teamService;
    private final ReportingService reportingService;
    private final ApiRouteCatalogService apiRouteCatalogService;

    public PlayerDashboardController(
            TournamentService tournamentService,
            TeamService teamService,
            ReportingService reportingService,
            ApiRouteCatalogService apiRouteCatalogService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
        this.reportingService = reportingService;
        this.apiRouteCatalogService = apiRouteCatalogService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("tournaments", tournamentService.listTournaments());
        model.addAttribute("teams", teamService.listTeams());
        model.addAttribute("metrics", reportingService.dashboardMetrics());
        model.addAttribute("apiRoutes", apiRouteCatalogService.playerRoutes());
        return "player-dashboard";
    }
}
