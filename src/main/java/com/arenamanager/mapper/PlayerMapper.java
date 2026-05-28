package com.arenamanager.mapper;

import com.arenamanager.domain.Player;
import com.arenamanager.domain.Team;
import com.arenamanager.dto.PlayerRequestDto;
import com.arenamanager.dto.PlayerResponseDto;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    private final PlayerProfileMapper playerProfileMapper;

    public PlayerMapper(PlayerProfileMapper playerProfileMapper) {
        this.playerProfileMapper = playerProfileMapper;
    }

    public Player toEntity(PlayerRequestDto dto) {
        Player player = new Player(dto.username(), dto.email());
        player.setProfile(playerProfileMapper.toEntity(dto.profile()));
        return player;
    }

    public PlayerResponseDto toResponse(Player player) {
        Team team = player.getTeam();
        return new PlayerResponseDto(
                player.getId(),
                player.getUsername(),
                player.getEmail(),
                team == null ? null : team.getId(),
                team == null ? null : team.getName(),
                playerProfileMapper.toDto(player.getProfile())
        );
    }
}
