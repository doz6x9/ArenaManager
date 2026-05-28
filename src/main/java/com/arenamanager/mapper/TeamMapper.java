package com.arenamanager.mapper;

import com.arenamanager.domain.Player;
import com.arenamanager.domain.Team;
import com.arenamanager.dto.TeamRequestDto;
import com.arenamanager.dto.TeamResponseDto;
import com.arenamanager.dto.TeamSummaryDto;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class TeamMapper {

    private final PlayerMapper playerMapper;

    public TeamMapper(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }

    public Team toEntity(TeamRequestDto dto) {
        return new Team(dto.name(), dto.tag().toUpperCase(), dto.maxRosterSize());
    }

    public TeamSummaryDto toSummary(Team team) {
        if (team == null) {
            return null;
        }
        return new TeamSummaryDto(team.getId(), team.getName(), team.getTag());
    }

    public TeamResponseDto toResponse(Team team) {
        List<Player> sortedPlayers = team.getPlayers().stream()
                .sorted(Comparator.comparing(Player::getUsername))
                .toList();
        return new TeamResponseDto(
                team.getId(),
                team.getName(),
                team.getTag(),
                team.getMaxRosterSize(),
                sortedPlayers.size(),
                sortedPlayers.stream().map(playerMapper::toResponse).toList()
        );
    }
}
