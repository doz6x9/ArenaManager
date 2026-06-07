package com.arenamanager.controller.web;

import com.arenamanager.service.PlayerService;
import com.arenamanager.service.ReportingService;
import com.arenamanager.service.TeamService;
import com.arenamanager.service.TournamentService;
import org.springframework.ui.Model;

public abstract class AbstractDashboardController extends AbstractWebController {

    private final TournamentService tournamentService;
    private final TeamService teamService;
    private final PlayerService playerService;
    private final ReportingService reportingService;

    protected AbstractDashboardController(
            TournamentService tournamentService,
            TeamService teamService,
            PlayerService playerService,
            ReportingService reportingService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
        this.playerService = playerService;
        this.reportingService = reportingService;
    }

    protected void addArenaOverview(Model model) {
        model.addAttribute("tournaments", tournamentService.listTournaments());
        model.addAttribute("teams", teamService.listTeams());
        model.addAttribute("players", playerService.listPlayers());
        model.addAttribute("metrics", reportingService.dashboardMetrics());
    }

    protected TeamService teamService() {
        return teamService;
    }

    protected ReportingService reportingService() {
        return reportingService;
    }
}
