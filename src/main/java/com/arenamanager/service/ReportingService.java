package com.arenamanager.service;

import com.arenamanager.domain.MatchStatus;
import com.arenamanager.dto.AuditLogResponseDto;
import com.arenamanager.dto.DashboardMetricsDto;
import com.arenamanager.mapper.AuditLogMapper;
import com.arenamanager.repository.AuditLogRepository;
import com.arenamanager.repository.BracketMatchRepository;
import com.arenamanager.repository.PlayerRepository;
import com.arenamanager.repository.TeamRepository;
import com.arenamanager.repository.TournamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportingService extends AbstractService {

    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final BracketMatchRepository bracketMatchRepository;
    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    public ReportingService(
            TournamentRepository tournamentRepository,
            TeamRepository teamRepository,
            PlayerRepository playerRepository,
            BracketMatchRepository bracketMatchRepository,
            AuditLogRepository auditLogRepository,
            AuditLogMapper auditLogMapper
    ) {
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.bracketMatchRepository = bracketMatchRepository;
        this.auditLogRepository = auditLogRepository;
        this.auditLogMapper = auditLogMapper;
    }

    @Override
    protected String serviceName() {
        return "reporting";
    }

    @Transactional(readOnly = true)
    public DashboardMetricsDto dashboardMetrics() {
        return new DashboardMetricsDto(
                tournamentRepository.count(),
                teamRepository.count(),
                playerRepository.count(),
                bracketMatchRepository.countByStatus(MatchStatus.SCHEDULED),
                bracketMatchRepository.countByStatus(MatchStatus.IN_PROGRESS),
                bracketMatchRepository.countByStatus(MatchStatus.COMPLETED),
                auditLogRepository.count()
        );
    }

    @Transactional(readOnly = true)
    public List<AuditLogResponseDto> recentAuditEvents() {
        return auditLogRepository.findTop10ByOrderByCreatedAtDesc().stream()
                .map(auditLogMapper::toResponse)
                .toList();
    }
}
