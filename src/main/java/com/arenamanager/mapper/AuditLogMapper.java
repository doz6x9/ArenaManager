package com.arenamanager.mapper;

import com.arenamanager.domain.AuditLog;
import com.arenamanager.dto.AuditLogResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ArenaMapperConfig.class)
public interface AuditLogMapper extends AbstractMapper {

    @Override
    default String mapperName() {
        return "audit-log";
    }

    @Mapping(target = "tournamentId", source = "tournament.id")
    @Mapping(target = "tournamentName", source = "tournament.name")
    @Mapping(target = "matchId", source = "match.id")
    AuditLogResponseDto toResponse(AuditLog auditLog);
}
