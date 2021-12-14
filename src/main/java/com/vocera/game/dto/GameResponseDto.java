package com.vocera.game.dto;

import lombok.Data;

@Data
public class GameResponseDto {

    private String serverMove;
    private Integer serverScore;
    private Integer yourScore;
}
