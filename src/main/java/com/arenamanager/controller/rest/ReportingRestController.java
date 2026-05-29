package com.arenamanager.controller.rest;

import com.arenamanager.dto.AuditLogResponseDto;
import com.arenamanager.dto.DashboardMetricsDto;
import com.arenamanager.service.ReportingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportingRestController {

    private final ReportingService reportingService;

    public ReportingRestController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/dashboard")
    public DashboardMetricsDto dashboard() {
        return reportingService.dashboardMetrics();
    }

    @GetMapping("/audit")
    public List<AuditLogResponseDto> auditTrail() {
        return reportingService.recentAuditEvents();
    }
}
