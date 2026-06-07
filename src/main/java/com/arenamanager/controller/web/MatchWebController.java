package com.arenamanager.controller.web;

import com.arenamanager.dto.ScoreUpdateForm;
import com.arenamanager.service.MatchService;
import com.arenamanager.service.TournamentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class MatchWebController extends AbstractWebController {

    private final TournamentService tournamentService;
    private final MatchService matchService;

    public MatchWebController(TournamentService tournamentService, MatchService matchService) {
        this.tournamentService = tournamentService;
        this.matchService = matchService;
    }

    @GetMapping("/tournaments/{tournamentId}/bracket")
    public String bracket(@PathVariable Long tournamentId, Model model, Authentication authentication) {
        boolean canManageBracket = hasRole(authentication, "ROLE_ORGANIZER");
        model.addAttribute("tournament", tournamentService.getTournament(tournamentId));
        model.addAttribute("matches", matchService.listMatchesForTournament(tournamentId));
        model.addAttribute("scoreUpdateForm", new ScoreUpdateForm());
        model.addAttribute("canManageBracket", canManageBracket);
        addHomeNavigation(model, authentication);
        return "bracket";
    }

    @PostMapping("/tournaments/{tournamentId}/bracket/generate")
    public String generateBracket(@PathVariable Long tournamentId, RedirectAttributes redirectAttributes) {
        tournamentService.generateBracket(tournamentId);
        redirectAttributes.addFlashAttribute("success", "Bracket generated");
        return "redirect:/tournaments/" + tournamentId + "/bracket";
    }

    @GetMapping("/tournaments/{tournamentId}/bracket/generate")
    public String generateBracketGet(@PathVariable Long tournamentId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("success", "Use the Generate bracket button to update the bracket.");
        return "redirect:/tournaments/" + tournamentId + "/bracket";
    }

    @PostMapping("/matches/score")
    public String updateScore(@ModelAttribute ScoreUpdateForm form, RedirectAttributes redirectAttributes) {
        matchService.updateScore(form.getMatchId(), form.getHomeScore(), form.getAwayScore());
        redirectAttributes.addFlashAttribute("success", "Score updated");
        return "redirect:/tournaments/" + form.getTournamentId() + "/bracket";
    }

}
