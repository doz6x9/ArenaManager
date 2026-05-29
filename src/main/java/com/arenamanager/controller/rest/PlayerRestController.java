package com.arenamanager.controller.rest;

import com.arenamanager.dto.PlayerRequestDto;
import com.arenamanager.dto.PlayerResponseDto;
import com.arenamanager.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerRestController {

    private final PlayerService playerService;

    public PlayerRestController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<PlayerResponseDto> listPlayers() {
        return playerService.listPlayers();
    }

    @GetMapping("/{id}")
    public PlayerResponseDto getPlayer(@PathVariable Long id) {
        return playerService.getPlayer(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerResponseDto createPlayer(@Valid @RequestBody PlayerRequestDto request) {
        return playerService.createPlayer(request);
    }
}
