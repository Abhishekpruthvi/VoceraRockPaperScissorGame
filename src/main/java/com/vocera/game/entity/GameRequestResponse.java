package com.vocera.game.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class GameRequestResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String token;

    @Column
    private String serverResponse;

    @Column
    private String clientResponse;

    @Column
    private Integer yourScore=0;

    @Column
    private Integer serverScore=0;

    @Column
    private String state;
}
