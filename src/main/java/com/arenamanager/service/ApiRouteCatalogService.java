package com.arenamanager.service;

import com.arenamanager.dto.ApiRouteDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiRouteCatalogService {

    private static final List<ApiRouteDto> ROUTES = List.of(
            new ApiRouteDto("POST", "/api/auth/token", "Authentication", "Public", "Issue a JWT bearer token for API clients."),
            new ApiRouteDto("GET", "/api/tournaments", "Tournaments", "Player + Captain + Organizer", "List tournament cards with registration and bracket summaries."),
            new ApiRouteDto("GET", "/api/tournaments/{id}", "Tournaments", "Player + Captain + Organizer", "Fetch one tournament with teams and matches."),
            new ApiRouteDto("GET", "/api/tournaments/{id}/bracket", "Tournaments", "Player + Captain + Organizer", "Fetch the live bracket for a tournament."),
            new ApiRouteDto("POST", "/api/tournaments", "Tournaments", "Organizer", "Create a tournament and optionally attach teams."),
            new ApiRouteDto("POST", "/api/tournaments/{id}/teams/{teamId}", "Tournaments", "Organizer", "Register a team into an open tournament."),
            new ApiRouteDto("POST", "/api/tournaments/{id}/bracket", "Tournaments", "Organizer", "Generate the first-round bracket."),
            new ApiRouteDto("GET", "/api/teams", "Teams", "Player + Captain + Organizer", "List teams with roster counts."),
            new ApiRouteDto("GET", "/api/teams/{id}", "Teams", "Player + Captain + Organizer", "Fetch a team and its roster."),
            new ApiRouteDto("POST", "/api/teams", "Teams", "Organizer", "Create a new team shell."),
            new ApiRouteDto("POST", "/api/teams/{teamId}/players/{playerId}", "Teams", "Organizer", "Attach a player to a team roster."),
            new ApiRouteDto("GET", "/api/players", "Players", "Player + Captain + Organizer", "List player identities and assigned teams."),
            new ApiRouteDto("GET", "/api/players/{id}", "Players", "Player + Captain + Organizer", "Fetch one player profile."),
            new ApiRouteDto("POST", "/api/players", "Players", "Organizer", "Create a player with profile metadata."),
            new ApiRouteDto("GET", "/api/matches?tournamentId={id}", "Matches", "Player + Captain + Organizer", "List matches for a tournament."),
            new ApiRouteDto("POST", "/api/matches", "Matches", "Organizer", "Create a match manually."),
            new ApiRouteDto("PUT", "/api/matches/{id}/score", "Matches", "Organizer", "Update scores and winner state."),
            new ApiRouteDto("GET", "/api/reports/dashboard", "Reports", "Organizer", "Read operational dashboard metrics."),
            new ApiRouteDto("GET", "/api/reports/audit", "Reports", "Organizer", "Read recent score and bracket audit events.")
    );

    public List<ApiRouteDto> listRoutes() {
        return ROUTES;
    }

    public List<ApiRouteDto> captainRoutes() {
        return ROUTES.stream()
                .filter(route -> route.access().contains("Captain") || route.access().equals("Public"))
                .toList();
    }

    public List<ApiRouteDto> playerRoutes() {
        return ROUTES.stream()
                .filter(route -> route.access().contains("Player") || route.access().equals("Public"))
                .toList();
    }
}
