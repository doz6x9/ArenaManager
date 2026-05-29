package com.arenamanager.controller.rest;

import com.arenamanager.dto.MatchRequestDto;
import com.arenamanager.dto.MatchResponseDto;
import com.arenamanager.dto.ScoreUpdateRequestDto;
import com.arenamanager.service.MatchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchRestController {

    private final MatchService matchService;

    public MatchRestController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public List<MatchResponseDto> listByTournament(@RequestParam Long tournamentId) {
        return matchService.listMatchesForTournament(tournamentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MatchResponseDto createMatch(@Valid @RequestBody MatchRequestDto request) {
        return matchService.createMatch(request);
    }

    @PutMapping("/{id}/score")
    public MatchResponseDto updateScore(@PathVariable Long id, @Valid @RequestBody ScoreUpdateRequestDto request) {
        return matchService.updateScore(id, request.homeScore(), request.awayScore());
    }
}
