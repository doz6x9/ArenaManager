package com.arenamanager.service;

import com.arenamanager.domain.BracketMatch;
import com.arenamanager.domain.MatchStatus;
import com.arenamanager.domain.Team;
import com.arenamanager.domain.Tournament;
import com.arenamanager.dto.MatchRequestDto;
import com.arenamanager.dto.MatchResponseDto;
import com.arenamanager.exception.BusinessRuleException;
import com.arenamanager.exception.ResourceNotFoundException;
import com.arenamanager.mapper.MatchMapper;
import com.arenamanager.repository.BracketMatchRepository;
import com.arenamanager.repository.TournamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatchService {

    private final BracketMatchRepository bracketMatchRepository;
    private final TournamentRepository tournamentRepository;
    private final TeamService teamService;
    private final MatchMapper matchMapper;

    public MatchService(
            BracketMatchRepository bracketMatchRepository,
            TournamentRepository tournamentRepository,
            TeamService teamService,
            MatchMapper matchMapper
    ) {
        this.bracketMatchRepository = bracketMatchRepository;
        this.tournamentRepository = tournamentRepository;
        this.teamService = teamService;
        this.matchMapper = matchMapper;
    }

    @Transactional
    public MatchResponseDto createMatch(MatchRequestDto request) {
        Tournament tournament = tournamentRepository.findById(request.tournamentId())
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found: " + request.tournamentId()));
        Team homeTeam = request.homeTeamId() == null ? null : teamService.requireTeam(request.homeTeamId());
        Team awayTeam = request.awayTeamId() == null ? null : teamService.requireTeam(request.awayTeamId());
        validateRegistered(tournament, homeTeam);
        validateRegistered(tournament, awayTeam);

        BracketMatch match = new BracketMatch(homeTeam, awayTeam, request.roundNumber(), request.bestOf());
        tournament.addMatch(match);
        tournamentRepository.save(tournament);
        return matchMapper.toResponse(bracketMatchRepository.save(match));
    }

    @Transactional(readOnly = true)
    public List<MatchResponseDto> listMatchesForTournament(Long tournamentId) {
        return bracketMatchRepository.findByTournamentIdOrderByRoundNumberAscIdAsc(tournamentId).stream()
                .map(matchMapper::toResponse)
                .toList();
    }

    @Transactional
    public MatchResponseDto updateScore(Long matchId, int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new BusinessRuleException("Scores must be zero or greater");
        }
        BracketMatch match = bracketMatchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + matchId));
        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
        applyStatusAndWinner(match);
        return matchMapper.toResponse(bracketMatchRepository.save(match));
    }

    private void validateRegistered(Tournament tournament, Team team) {
        if (team != null && !tournament.getRegisteredTeams().contains(team)) {
            throw new BusinessRuleException("Team is not registered for this tournament: " + team.getName());
        }
    }

    private void applyStatusAndWinner(BracketMatch match) {
        int winsRequired = (match.getBestOf() / 2) + 1;
        match.setWinnerTeam(null);

        if (match.getHomeScore() == 0 && match.getAwayScore() == 0) {
            match.setStatus(MatchStatus.SCHEDULED);
            return;
        }

        if (match.getHomeScore() >= winsRequired && match.getHomeScore() > match.getAwayScore()) {
            match.setWinnerTeam(match.getHomeTeam());
            match.setStatus(MatchStatus.COMPLETED);
            return;
        }

        if (match.getAwayScore() >= winsRequired && match.getAwayScore() > match.getHomeScore()) {
            match.setWinnerTeam(match.getAwayTeam());
            match.setStatus(MatchStatus.COMPLETED);
            return;
        }

        match.setStatus(MatchStatus.IN_PROGRESS);
    }
}
