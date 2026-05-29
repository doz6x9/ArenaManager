package com.arenamanager.config;

import com.arenamanager.domain.BracketMatch;
import com.arenamanager.domain.Player;
import com.arenamanager.domain.PlayerProfile;
import com.arenamanager.domain.Team;
import com.arenamanager.domain.Tournament;
import com.arenamanager.repository.TeamRepository;
import com.arenamanager.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoDataConfig {

    @Bean
    CommandLineRunner seedDemoData(
            TeamRepository teamRepository,
            TournamentRepository tournamentRepository,
            @Value("${arena.demo-data:true}") boolean demoData
    ) {
        return args -> {
            if (!demoData || teamRepository.count() > 0) {
                return;
            }

            Team sentinels = team("Sentinels", "SEN",
                    player("TenZ", 800, "claw", "Mechanical star power and highlight entry rounds."),
                    player("zekken", 800, "fingertip", "Fast duelist pressure and late-round confidence."));

            Team fnatic = team("FNATIC", "FNC",
                    player("Boaster", 400, "palm", "In-game leader with structured mid-round calls."),
                    player("Chronicle", 800, "claw", "Flexible controller and initiator support."));

            Team paperRex = team("Paper Rex", "PRX",
                    player("f0rsakeN", 800, "claw", "Explosive flex player with fearless space-taking."),
                    player("Jinggg", 800, "fingertip", "Tempo-setting entry specialist."));

            Team genG = team("Gen.G", "GENG",
                    player("t3xture", 800, "claw", "High-impact duelist with sharp first-contact aim."),
                    player("Meteor", 800, "palm", "Reliable sentinel anchor and clutch closer."));

            Team g2 = team("G2 Esports", "G2",
                    player("valyn", 800, "palm", "Composed caller with strong map control."),
                    player("leaf", 800, "claw", "Flexible rifle threat across roles."));

            Team heretics = team("Team Heretics", "TH",
                    player("benjyfishy", 800, "fingertip", "Sentinel star with elite conversion rounds."),
                    player("Wo0t", 800, "claw", "Explosive aim and confident duel selection."));

            Team nrg = team("NRG", "NRG",
                    player("FNS", 400, "palm", "Veteran shot caller and defensive organizer."),
                    player("s0m", 800, "claw", "Controller player with clutch utility timing."));

            Team edg = team("EDward Gaming", "EDG",
                    player("ZmjjKK", 800, "fingertip", "Operator pressure and fearless opening duels."),
                    player("nobody", 800, "claw", "Initiator utility and disciplined retake setups."));

            teamRepository.save(sentinels);
            teamRepository.save(fnatic);
            teamRepository.save(paperRex);
            teamRepository.save(genG);
            teamRepository.save(g2);
            teamRepository.save(heretics);
            teamRepository.save(nrg);
            teamRepository.save(edg);

            Tournament championsShowcase = new Tournament("VCT Champions Showcase", "VALORANT", 8, true);
            championsShowcase.registerTeam(sentinels);
            championsShowcase.registerTeam(fnatic);
            championsShowcase.registerTeam(paperRex);
            championsShowcase.registerTeam(genG);
            championsShowcase.registerTeam(g2);
            championsShowcase.registerTeam(heretics);
            championsShowcase.registerTeam(nrg);
            championsShowcase.registerTeam(edg);
            championsShowcase.addMatch(new BracketMatch(sentinels, fnatic, 1, 3));
            championsShowcase.addMatch(new BracketMatch(paperRex, genG, 1, 3));
            championsShowcase.addMatch(new BracketMatch(g2, heretics, 1, 3));
            championsShowcase.addMatch(new BracketMatch(nrg, edg, 1, 3));
            tournamentRepository.save(championsShowcase);
        };
    }

    private Team team(String name, String tag, Player... players) {
        Team team = new Team(name, tag, 5);
        for (Player player : players) {
            team.addPlayer(player);
        }
        return team;
    }

    private Player player(String handle, int dpi, String grip, String bio) {
        Player player = new Player(handle, handle.toLowerCase() + "@arena.demo");
        player.setProfile(new PlayerProfile(dpi, grip, bio));
        return player;
    }
}
