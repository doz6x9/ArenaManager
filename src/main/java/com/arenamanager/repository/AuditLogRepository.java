package com.arenamanager.repository;

import com.arenamanager.domain.AuditLog;

import java.util.List;

public interface AuditLogRepository extends AbstractRepository<AuditLog> {

    List<AuditLog> findTop10ByOrderByCreatedAtDesc();
}
