package com.arenamanager.service;

import com.arenamanager.domain.Player;
import com.arenamanager.domain.PlayerProfile;
import com.arenamanager.domain.UserAccount;
import com.arenamanager.dto.PlayerResponseDto;
import com.arenamanager.dto.RegistrationRequestDto;
import com.arenamanager.exception.BusinessRuleException;
import com.arenamanager.mapper.PlayerMapper;
import com.arenamanager.repository.PlayerRepository;
import com.arenamanager.repository.UserAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Set;

@Service
public class RegistrationService extends AbstractService {

    private static final Set<String> RESERVED_USERNAMES = Set.of("organizer", "captain", "player");

    private final PlayerRepository playerRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlayerMapper playerMapper;

    public RegistrationService(
            PlayerRepository playerRepository,
            UserAccountRepository userAccountRepository,
            PasswordEncoder passwordEncoder,
            PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.playerMapper = playerMapper;
    }

    @Override
    protected String serviceName() {
        return "registration";
    }

    @Transactional
    public PlayerResponseDto registerPlayer(RegistrationRequestDto request) {
        String username = clean(request.getUsername());
        String email = clean(request.getEmail()).toLowerCase(Locale.ROOT);

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BusinessRuleException("Passwords do not match");
        }
        if (RESERVED_USERNAMES.contains(username.toLowerCase(Locale.ROOT))) {
            throw new BusinessRuleException("Username is reserved for demo access");
        }
        if (playerRepository.existsByUsername(username) || userAccountRepository.existsByUsername(username)) {
            throw new BusinessRuleException("Username already exists");
        }
        if (playerRepository.existsByEmail(email)) {
            throw new BusinessRuleException("Email already exists");
        }

        Player player = new Player(username, email);
        player.setProfile(new PlayerProfile(
                request.getPreferredPeripheralDpi(),
                cleanNullable(request.getMouseGripStyle()),
                cleanNullable(request.getBio())
        ));
        Player savedPlayer = playerRepository.save(player);

        UserAccount account = new UserAccount(
                savedPlayer.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                "PLAYER",
                savedPlayer
        );
        userAccountRepository.save(account);

        return playerMapper.toResponse(savedPlayer);
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }

    private String cleanNullable(String value) {
        String cleaned = clean(value);
        return cleaned.isBlank() ? null : cleaned;
    }
}
