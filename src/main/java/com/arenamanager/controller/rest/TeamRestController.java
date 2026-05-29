package com.arenamanager.controller.rest;

import com.arenamanager.dto.TeamRequestDto;
import com.arenamanager.dto.TeamResponseDto;
import com.arenamanager.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamRestController {

    private final TeamService teamService;

    public TeamRestController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<TeamResponseDto> listTeams() {
        return teamService.listTeams();
    }

    @GetMapping("/{id}")
    public TeamResponseDto getTeam(@PathVariable Long id) {
        return teamService.getTeam(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamResponseDto createTeam(@Valid @RequestBody TeamRequestDto request) {
        return teamService.createTeam(request);
    }

    @PostMapping("/{teamId}/players/{playerId}")
    public TeamResponseDto addPlayer(@PathVariable Long teamId, @PathVariable Long playerId) {
        return teamService.addPlayerToTeam(teamId, playerId);
    }
}
