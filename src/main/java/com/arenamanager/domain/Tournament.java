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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tournaments")
public class Tournament extends AbstractEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 80)
    private String gameTitle;

    @Column(nullable = false)
    private int maxTeams;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public int getMaxTeams() {
        return maxTeams;
    }

    public void setMaxTeams(int maxTeams) {
        this.maxTeams = maxTeams;
    }

    public boolean isRegistrationOpen() {
        return registrationOpen;
    }

    public void setRegistrationOpen(boolean registrationOpen) {
        this.registrationOpen = registrationOpen;
    }

    public Set<Team> getRegisteredTeams() {
        return registeredTeams;
    }

    public List<BracketMatch> getMatches() {
        return matches;
    }
}
