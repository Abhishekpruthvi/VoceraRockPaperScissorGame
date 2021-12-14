package com.vocera.game.dto;

import lombok.Data;

@Data
public class StartResponseDto {
    private String token;
    private String response="READY";
}
