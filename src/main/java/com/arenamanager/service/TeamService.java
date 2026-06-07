package com.arenamanager.service;

import com.arenamanager.domain.Player;
import com.arenamanager.domain.Team;
import com.arenamanager.dto.TeamRequestDto;
import com.arenamanager.dto.TeamResponseDto;
import com.arenamanager.exception.BusinessRuleException;
import com.arenamanager.exception.ResourceNotFoundException;
import com.arenamanager.exception.RosterFullException;
import com.arenamanager.mapper.TeamMapper;
import com.arenamanager.repository.PlayerRepository;
import com.arenamanager.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class TeamService extends AbstractService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final TeamMapper teamMapper;

    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    protected String serviceName() {
        return "team";
    }

    @Transactional
    public TeamResponseDto createTeam(TeamRequestDto request) {
        String tag = request.tag().toUpperCase();
        if (teamRepository.existsByName(request.name())) {
            throw new BusinessRuleException("Team name already exists");
        }
        if (teamRepository.existsByTag(tag)) {
            throw new BusinessRuleException("Team tag already exists");
        }
        Team team = teamMapper.toEntity(request);
        return teamMapper.toResponse(teamRepository.save(team));
    }

    @Transactional(readOnly = true)
    public List<TeamResponseDto> listTeams() {
        return teamRepository.findAll().stream()
                .sorted(Comparator.comparing(Team::getName))
                .map(teamMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TeamResponseDto getTeam(Long id) {
        return teamMapper.toResponse(requireTeam(id));
    }

    @Transactional
    public TeamResponseDto addPlayerToTeam(Long teamId, Long playerId) {
        Team team = requireTeam(teamId);
        Player player = requireFound(
                playerRepository.findById(playerId),
                () -> new ResourceNotFoundException("Player not found: " + playerId));
        if (team.isRosterFull()) {
            throw new RosterFullException("Team roster is full: " + team.getName());
        }
        if (player.getTeam() != null && player.getTeam().getId().equals(teamId)) {
            return teamMapper.toResponse(team);
        }
        team.addPlayer(player);
        return teamMapper.toResponse(teamRepository.save(team));
    }

    Team requireTeam(Long id) {
        return requireFound(
                teamRepository.findById(id),
                () -> new ResourceNotFoundException("Team not found: " + id));
    }
}
