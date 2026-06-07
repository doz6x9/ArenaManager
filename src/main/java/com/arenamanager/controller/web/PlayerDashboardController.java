package com.arenamanager.controller.web;

import com.arenamanager.service.ApiRouteCatalogService;
import com.arenamanager.service.PlayerService;
import com.arenamanager.service.ReportingService;
import com.arenamanager.service.TeamService;
import com.arenamanager.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/player")
public class PlayerDashboardController extends AbstractDashboardController {

    private final ApiRouteCatalogService apiRouteCatalogService;

    public PlayerDashboardController(
            TournamentService tournamentService,
            TeamService teamService,
            PlayerService playerService,
            ReportingService reportingService,
            ApiRouteCatalogService apiRouteCatalogService) {
        super(tournamentService, teamService, playerService, reportingService);
        this.apiRouteCatalogService = apiRouteCatalogService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        addArenaOverview(model);
        model.addAttribute("apiRoutes", apiRouteCatalogService.playerRoutes());
        return "player-dashboard";
    }
}
