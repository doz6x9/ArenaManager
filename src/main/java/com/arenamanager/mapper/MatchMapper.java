package com.arenamanager.mapper;

import com.arenamanager.domain.BracketMatch;
import com.arenamanager.dto.MatchResponseDto;
import org.springframework.stereotype.Component;

@Component
public class MatchMapper {

    private final TeamMapper teamMapper;

    public MatchMapper(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    public MatchResponseDto toResponse(BracketMatch match) {
        return new MatchResponseDto(
                match.getId(),
                match.getTournament().getId(),
                teamMapper.toSummary(match.getHomeTeam()),
                teamMapper.toSummary(match.getAwayTeam()),
                teamMapper.toSummary(match.getWinnerTeam()),
                match.getRoundNumber(),
                match.getBestOf(),
                match.getHomeScore(),
                match.getAwayScore(),
                match.getStatus().name()
        );
    }
}
