package com.arenamanager.mapper;

import com.arenamanager.domain.BracketMatch;
import com.arenamanager.dto.MatchResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ArenaMapperConfig.class, uses = TeamMapper.class)
public interface MatchMapper extends AbstractMapper {

    @Override
    default String mapperName() {
        return "match";
    }

    @Mapping(target = "tournamentId", source = "tournament.id")
    @Mapping(target = "status", expression = "java(match.getStatus().name())")
    MatchResponseDto toResponse(BracketMatch match);
}
