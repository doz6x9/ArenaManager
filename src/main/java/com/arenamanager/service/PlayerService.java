package com.arenamanager.service;

import com.arenamanager.domain.Player;
import com.arenamanager.domain.Team;
import com.arenamanager.dto.PlayerRequestDto;
import com.arenamanager.dto.PlayerResponseDto;
import com.arenamanager.exception.BusinessRuleException;
import com.arenamanager.exception.ResourceNotFoundException;
import com.arenamanager.exception.RosterFullException;
import com.arenamanager.mapper.PlayerMapper;
import com.arenamanager.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class PlayerService extends AbstractService {

    private final PlayerRepository playerRepository;
    private final TeamService teamService;
    private final PlayerMapper playerMapper;

    public PlayerService(PlayerRepository playerRepository, TeamService teamService, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.teamService = teamService;
        this.playerMapper = playerMapper;
    }

    @Override
    protected String serviceName() {
        return "player";
    }

    @Transactional
    public PlayerResponseDto createPlayer(PlayerRequestDto request) {
        if (playerRepository.existsByUsername(request.username())) {
            throw new BusinessRuleException("Player username already exists");
        }
        if (playerRepository.existsByEmail(request.email())) {
            throw new BusinessRuleException("Player email already exists");
        }
        Player player = playerMapper.toEntity(request);
        if (request.teamId() != null) {
            Team team = teamService.requireTeam(request.teamId());
            if (team.isRosterFull()) {
                throw new RosterFullException("Team roster is full: " + team.getName());
            }
            team.addPlayer(player);
        }
        return playerMapper.toResponse(playerRepository.save(player));
    }

    @Transactional(readOnly = true)
    public List<PlayerResponseDto> listPlayers() {
        return playerRepository.findAll().stream()
                .sorted(Comparator.comparing(Player::getUsername))
                .map(playerMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlayerResponseDto getPlayer(Long id) {
        return playerMapper.toResponse(requirePlayer(id));
    }

    Player requirePlayer(Long id) {
        return requireFound(
                playerRepository.findById(id),
                () -> new ResourceNotFoundException("Player not found: " + id));
    }
}
