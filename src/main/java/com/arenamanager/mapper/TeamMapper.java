package com.arenamanager.mapper;

import com.arenamanager.domain.Team;
import com.arenamanager.dto.TeamRequestDto;
import com.arenamanager.dto.TeamResponseDto;
import com.arenamanager.dto.TeamSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Locale;

@Mapper(config = ArenaMapperConfig.class, uses = PlayerMapper.class, imports = Locale.class)
public interface TeamMapper {

    @Mapping(target = "tag", expression = "java(dto.tag().toUpperCase(Locale.ROOT))")
    Team toEntity(TeamRequestDto dto);

    TeamSummaryDto toSummary(Team team);

    @Mapping(target = "rosterCount", expression = "java(team.getPlayers().size())")
    TeamResponseDto toResponse(Team team);
}
