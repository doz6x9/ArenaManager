package com.arenamanager.controller.rest;

import com.arenamanager.dto.TournamentRequestDto;
import com.arenamanager.dto.TournamentResponseDto;
import com.arenamanager.service.TournamentService;
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
@RequestMapping("/api/tournaments")
public class TournamentRestController {

    private final TournamentService tournamentService;

    public TournamentRestController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public List<TournamentResponseDto> listTournaments() {
        return tournamentService.listTournaments();
    }

    @GetMapping("/{id}")
    public TournamentResponseDto getTournament(@PathVariable Long id) {
        return tournamentService.getTournament(id);
    }

    @GetMapping("/{id}/bracket")
    public TournamentResponseDto getBracket(@PathVariable Long id) {
        return tournamentService.getTournament(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TournamentResponseDto createTournament(@Valid @RequestBody TournamentRequestDto request) {
        return tournamentService.createTournament(request);
    }

    @PostMapping("/{id}/teams/{teamId}")
    public TournamentResponseDto registerTeam(@PathVariable Long id, @PathVariable Long teamId) {
        return tournamentService.registerTeam(id, teamId);
    }

    @PostMapping("/{id}/bracket")
    public TournamentResponseDto generateBracket(@PathVariable Long id) {
        return tournamentService.generateBracket(id);
    }
}
