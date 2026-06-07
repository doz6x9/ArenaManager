package com.arenamanager.service;

import com.arenamanager.domain.BracketMatch;
import com.arenamanager.domain.MatchStatus;
import com.arenamanager.domain.Team;
import com.arenamanager.domain.Tournament;
import com.arenamanager.dto.TournamentRequestDto;
import com.arenamanager.dto.TournamentResponseDto;
import com.arenamanager.exception.BusinessRuleException;
import com.arenamanager.exception.ResourceNotFoundException;
import com.arenamanager.exception.TournamentFullException;
import com.arenamanager.mapper.TournamentMapper;
import com.arenamanager.repository.TournamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TournamentService extends AbstractService {

    private final TournamentRepository tournamentRepository;
    private final TeamService teamService;
    private final TournamentMapper tournamentMapper;

    public TournamentService(TournamentRepository tournamentRepository, TeamService teamService, TournamentMapper tournamentMapper) {
        this.tournamentRepository = tournamentRepository;
        this.teamService = teamService;
        this.tournamentMapper = tournamentMapper;
    }

    @Override
    protected String serviceName() {
        return "tournament";
    }

    @Transactional
    public TournamentResponseDto createTournament(TournamentRequestDto request) {
        Tournament tournament = tournamentMapper.toEntity(request);
        if (request.teamIds() != null) {
            for (Long teamId : request.teamIds()) {
                registerTeam(tournament, teamService.requireTeam(teamId));
            }
        }
        return tournamentMapper.toResponse(tournamentRepository.save(tournament));
    }

    @Transactional(readOnly = true)
    public List<TournamentResponseDto> listTournaments() {
        return tournamentRepository.findAll().stream()
                .sorted(Comparator.comparing(Tournament::getName))
                .map(tournamentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TournamentResponseDto getTournament(Long id) {
        return tournamentMapper.toResponse(requireTournament(id));
    }

    @Transactional
    public TournamentResponseDto registerTeam(Long tournamentId, Long teamId) {
        Tournament tournament = requireTournament(tournamentId);
        Team team = teamService.requireTeam(teamId);
        registerTeam(tournament, team);
        return tournamentMapper.toResponse(tournamentRepository.save(tournament));
    }

    @Transactional
    public TournamentResponseDto generateBracket(Long tournamentId) {
        Tournament tournament = requireTournament(tournamentId);
        List<Team> teams = new ArrayList<>(tournament.getRegisteredTeams());
        if (teams.size() < 2) {
            throw new BusinessRuleException("At least two teams are required to generate a bracket");
        }

        tournament.getMatches().clear();
        for (int i = 0; i < teams.size(); i += 2) {
            Team home = teams.get(i);
            Team away = i + 1 < teams.size() ? teams.get(i + 1) : null;
            BracketMatch match = new BracketMatch(home, away, 1, 3);
            if (away == null) {
                match.setHomeScore(1);
                match.setWinnerTeam(home);
                match.setStatus(MatchStatus.COMPLETED);
            }
            tournament.addMatch(match);
        }

        return tournamentMapper.toResponse(tournamentRepository.save(tournament));
    }

    Tournament requireTournament(Long id) {
        return requireFound(
                tournamentRepository.findById(id),
                () -> new ResourceNotFoundException("Tournament not found: " + id));
    }

    private void registerTeam(Tournament tournament, Team team) {
        if (!tournament.isRegistrationOpen()) {
            throw new BusinessRuleException("Tournament registration is closed");
        }
        if (tournament.getRegisteredTeams().contains(team)) {
            return;
        }
        if (tournament.isFull()) {
            throw new TournamentFullException("Tournament is full: " + tournament.getName());
        }
        tournament.registerTeam(team);
    }
}
