package com.arenamanager.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "teams")
public class Team extends AbstractEntity {

    @Column(nullable = false, unique = true, length = 80)
    private String name;

    @Column(nullable = false, unique = true, length = 12)
    private String tag;

    @Column(nullable = false)
    private int maxRosterSize;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Player> players = new ArrayList<>();

    @ManyToMany(mappedBy = "registeredTeams")
    private Set<Tournament> registeredTournaments = new HashSet<>();

    protected Team() {
    }

    public Team(String name, String tag, int maxRosterSize) {
        this.name = name;
        this.tag = tag;
        this.maxRosterSize = maxRosterSize;
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.setTeam(this);
    }

    public void removePlayer(Player player) {
        players.remove(player);
        player.setTeam(null);
    }

    public boolean isRosterFull() {
        return players.size() >= maxRosterSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getMaxRosterSize() {
        return maxRosterSize;
    }

    public void setMaxRosterSize(int maxRosterSize) {
        this.maxRosterSize = maxRosterSize;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Set<Tournament> getRegisteredTournaments() {
        return registeredTournaments;
    }
}
