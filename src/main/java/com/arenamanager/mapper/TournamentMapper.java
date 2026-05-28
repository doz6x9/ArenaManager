package com.arenamanager.mapper;

import com.arenamanager.domain.BracketMatch;
import com.arenamanager.domain.Team;
import com.arenamanager.domain.Tournament;
import com.arenamanager.dto.TournamentRequestDto;
import com.arenamanager.dto.TournamentResponseDto;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class TournamentMapper {

    private final TeamMapper teamMapper;
    private final MatchMapper matchMapper;

    public TournamentMapper(TeamMapper teamMapper, MatchMapper matchMapper) {
        this.teamMapper = teamMapper;
        this.matchMapper = matchMapper;
    }

    public Tournament toEntity(TournamentRequestDto dto) {
        return new Tournament(dto.name(), dto.gameTitle(), dto.maxTeams(), dto.registrationOpen());
    }

    public TournamentResponseDto toResponse(Tournament tournament) {
        List<Team> teams = tournament.getRegisteredTeams().stream()
                .sorted(Comparator.comparing(Team::getName))
                .toList();
        List<BracketMatch> matches = tournament.getMatches().stream()
                .sorted(Comparator.comparingInt(BracketMatch::getRoundNumber)
                        .thenComparing(BracketMatch::getId, Comparator.nullsLast(Long::compareTo)))
                .toList();
        return new TournamentResponseDto(
                tournament.getId(),
                tournament.getName(),
                tournament.getGameTitle(),
                tournament.getMaxTeams(),
                tournament.isRegistrationOpen(),
                teams.stream().map(teamMapper::toSummary).toList(),
                matches.stream().map(matchMapper::toResponse).toList()
        );
    }
}
