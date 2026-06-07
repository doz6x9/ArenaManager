package com.arenamanager.mapper;

import com.arenamanager.domain.Player;
import com.arenamanager.dto.PlayerRequestDto;
import com.arenamanager.dto.PlayerResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ArenaMapperConfig.class, uses = PlayerProfileMapper.class)
public interface PlayerMapper extends AbstractMapper {

    @Override
    default String mapperName() {
        return "player";
    }

    @Mapping(target = "team", ignore = true)
    Player toEntity(PlayerRequestDto dto);

    @Mapping(target = "teamId", source = "team.id")
    @Mapping(target = "teamName", source = "team.name")
    PlayerResponseDto toResponse(Player player);
}
