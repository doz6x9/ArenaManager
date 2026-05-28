package com.arenamanager.mapper;

import com.arenamanager.domain.PlayerProfile;
import com.arenamanager.dto.PlayerProfileDto;
import org.springframework.stereotype.Component;

@Component
public class PlayerProfileMapper {

    public PlayerProfile toEntity(PlayerProfileDto dto) {
        if (dto == null) {
            return null;
        }
        return new PlayerProfile(dto.preferredPeripheralDpi(), dto.mouseGripStyle(), dto.bio());
    }

    public PlayerProfileDto toDto(PlayerProfile profile) {
        if (profile == null) {
            return null;
        }
        return new PlayerProfileDto(
                profile.getPreferredPeripheralDpi(),
                profile.getMouseGripStyle(),
                profile.getBio()
        );
    }
}
