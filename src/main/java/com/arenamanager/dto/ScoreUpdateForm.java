package com.arenamanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreUpdateForm {

    private Long matchId;
    private Long tournamentId;
    private int homeScore;
    private int awayScore;
}
