package com.arenamanager.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "audit_logs")
public class AuditLog extends AbstractEntity {

    @Column(nullable = false, length = 80)
    private String action;

    @Column(nullable = false, length = 80)
    private String actor;

    @Column(nullable = false, length = 500)
    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private BracketMatch match;

    protected AuditLog() {
    }

    public AuditLog(String action, String actor, String details, Tournament tournament, BracketMatch match) {
        this.action = action;
        this.actor = actor;
        this.details = details;
        this.tournament = tournament;
        this.match = match;
    }

    public String getAction() {
        return action;
    }

    public String getActor() {
        return actor;
    }

    public String getDetails() {
        return details;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public BracketMatch getMatch() {
        return match;
    }
}
