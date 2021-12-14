package com.vocera.game.repository;

import com.vocera.game.entity.GameRequestResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameRequestResponse,Integer> {

    GameRequestResponse findByToken(String token);
}
