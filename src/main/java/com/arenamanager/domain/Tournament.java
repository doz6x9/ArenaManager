package com.arenamanager.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "tournaments")
public class Tournament extends AbstractEntity {

    @Setter
    @Column(nullable = false, length = 100)
    private String name;

    @Setter
    @Column(nullable = false, length = 80)
    private String gameTitle;

    @Setter
    @Column(nullable = false)
    private int maxTeams;

    @Setter
    @Column(nullable = false)
    private boolean registrationOpen;

    @ManyToMany
    @JoinTable(
            name = "tournament_teams",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    @OrderBy("name ASC")
    private Set<Team> registeredTeams = new LinkedHashSet<>();

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("roundNumber ASC, id ASC")
    private List<BracketMatch> matches = new ArrayList<>();

    protected Tournament() {
    }

    public Tournament(String name, String gameTitle, int maxTeams, boolean registrationOpen) {
        this.name = name;
        this.gameTitle = gameTitle;
        this.maxTeams = maxTeams;
        this.registrationOpen = registrationOpen;
    }

    public void registerTeam(Team team) {
        registeredTeams.add(team);
        team.getRegisteredTournaments().add(this);
    }

    public void addMatch(BracketMatch match) {
        matches.add(match);
        match.setTournament(this);
    }

    public boolean isFull() {
        return registeredTeams.size() >= maxTeams;
    }

}
