package com.arenamanager.controller.web;

import com.arenamanager.dto.TeamRequestDto;
import com.arenamanager.service.TeamService;
import com.arenamanager.service.TournamentService;
import com.arenamanager.service.ReportingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final TournamentService tournamentService;
    private final TeamService teamService;
    private final ReportingService reportingService;

    public AdminDashboardController(TournamentService tournamentService, TeamService teamService, ReportingService reportingService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
        this.reportingService = reportingService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("tournaments", tournamentService.listTournaments());
        model.addAttribute("teams", teamService.listTeams());
        model.addAttribute("metrics", reportingService.dashboardMetrics());
        model.addAttribute("auditEvents", reportingService.recentAuditEvents());
        return "dashboard";
    }

    @PostMapping("/teams")
    public String createTeam(
            @RequestParam String name,
            @RequestParam String tag,
            @RequestParam int maxRosterSize,
            RedirectAttributes redirectAttributes) {
        try {
            TeamRequestDto request = new TeamRequestDto(name, tag, maxRosterSize);
            teamService.createTeam(request);
            redirectAttributes.addFlashAttribute("success", "Team '" + name + "' created successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create team: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }
}
