package com.arenamanager.mapper;

import com.arenamanager.domain.PlayerProfile;
import com.arenamanager.dto.PlayerProfileDto;
import org.mapstruct.Mapper;

@Mapper(config = ArenaMapperConfig.class)
public interface PlayerProfileMapper extends AbstractMapper {

    @Override
    default String mapperName() {
        return "player-profile";
    }

    PlayerProfile toEntity(PlayerProfileDto dto);

    PlayerProfileDto toDto(PlayerProfile profile);
}
