package com.arenamanager.repository;

import com.arenamanager.domain.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findTop10ByOrderByCreatedAtDesc();
}
