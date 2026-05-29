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

            Team pixelForge = new Team("Pixel Forge", "PXF", 5);
            Player nova = new Player("Nova", "nova@example.com");
            nova.setProfile(new PlayerProfile(800, "claw", "Entry fragger with fast retakes."));
            Player drift = new Player("Drift", "drift@example.com");
            drift.setProfile(new PlayerProfile(1200, "palm", "Shot caller and utility lead."));
            pixelForge.addPlayer(nova);
            pixelForge.addPlayer(drift);

            Team latencyZero = new Team("Latency Zero", "LZ", 5);
            Player pulse = new Player("Pulse", "pulse@example.com");
            pulse.setProfile(new PlayerProfile(1600, "fingertip", "Long-range specialist."));
            latencyZero.addPlayer(pulse);

            Team stackTrace = new Team("Stack Trace", "STK", 5);
            Player cache = new Player("Cache", "cache@example.com");
            cache.setProfile(new PlayerProfile(1000, "claw", "Anchor and clutch player."));
            stackTrace.addPlayer(cache);

            teamRepository.save(pixelForge);
            teamRepository.save(latencyZero);
            teamRepository.save(stackTrace);

            Tournament springLan = new Tournament("Spring LAN Invitational", "Counter-Strike 2", 8, true);
            springLan.registerTeam(pixelForge);
            springLan.registerTeam(latencyZero);
            springLan.registerTeam(stackTrace);
            springLan.addMatch(new BracketMatch(pixelForge, latencyZero, 1, 3));
            springLan.addMatch(new BracketMatch(stackTrace, null, 1, 3));
            tournamentRepository.save(springLan);
        };
    }
}
