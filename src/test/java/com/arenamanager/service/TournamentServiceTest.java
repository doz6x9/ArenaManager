package com.arenamanager.service;

import com.arenamanager.dto.TeamRequestDto;
import com.arenamanager.dto.TeamResponseDto;
import com.arenamanager.dto.TournamentRequestDto;
import com.arenamanager.dto.TournamentResponseDto;
import com.arenamanager.exception.TournamentFullException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(properties = "arena.demo-data=false")
@Transactional
class TournamentServiceTest {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TournamentService tournamentService;

    @Test
    void registerTeamRejectsFullTournament() {
        TeamResponseDto alpha = teamService.createTeam(new TeamRequestDto("Alpha", "ALP", 5));
        TeamResponseDto beta = teamService.createTeam(new TeamRequestDto("Beta", "BET", 5));
        TournamentResponseDto tournament = tournamentService.createTournament(
                new TournamentRequestDto("One Slot Cup", "Valorant", 2, true, Set.of(alpha.id(), beta.id()))
        );
        TeamResponseDto gamma = teamService.createTeam(new TeamRequestDto("Gamma", "GAM", 5));

        assertThatThrownBy(() -> tournamentService.registerTeam(tournament.id(), gamma.id()))
                .isInstanceOf(TournamentFullException.class);
    }

    @Test
    void generateBracketCreatesRoundOneMatchesFromRegisteredTeams() {
        TeamResponseDto alpha = teamService.createTeam(new TeamRequestDto("Alpha Two", "A2", 5));
        TeamResponseDto beta = teamService.createTeam(new TeamRequestDto("Beta Two", "B2", 5));
        TournamentResponseDto tournament = tournamentService.createTournament(
                new TournamentRequestDto("Duo Cup", "Rocket League", 8, true, Set.of(alpha.id(), beta.id()))
        );

        TournamentResponseDto bracket = tournamentService.generateBracket(tournament.id());

        assertThat(bracket.matches()).hasSize(1);
        assertThat(bracket.matches().get(0).roundNumber()).isEqualTo(1);
        assertThat(bracket.matches().get(0).homeTeam()).isNotNull();
        assertThat(bracket.matches().get(0).awayTeam()).isNotNull();
    }
}
