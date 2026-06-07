package com.arenamanager.controller.web;

import com.arenamanager.service.TeamService;
import com.arenamanager.service.TournamentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TournamentWebController extends AbstractWebController {

    private final TournamentService tournamentService;
    private final TeamService teamService;

    public TournamentWebController(TournamentService tournamentService, TeamService teamService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
    }

    @GetMapping("/tournaments")
    public String listTournaments(Model model, Authentication authentication) {
        model.addAttribute("tournaments", tournamentService.listTournaments());
        model.addAttribute("teams", teamService.listTeams());
        addHomeNavigation(model, authentication);
        return "tournaments";
    }
}
