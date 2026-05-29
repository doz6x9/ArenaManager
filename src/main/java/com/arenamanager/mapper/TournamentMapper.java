package com.arenamanager.mapper;

import com.arenamanager.domain.Tournament;
import com.arenamanager.dto.TournamentRequestDto;
import com.arenamanager.dto.TournamentResponseDto;
import org.mapstruct.Mapper;

@Mapper(config = ArenaMapperConfig.class, uses = {TeamMapper.class, MatchMapper.class})
public interface TournamentMapper {

    Tournament toEntity(TournamentRequestDto dto);

    TournamentResponseDto toResponse(Tournament tournament);
}
