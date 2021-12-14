package com.vocera.game.service;

import com.vocera.game.dto.GameResponseDto;
import com.vocera.game.dto.StartResponseDto;
import com.vocera.game.entity.GameRequestResponse;
import com.vocera.game.repository.GameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GameControllerService {
    private static final Logger LOGGER = LogManager.getLogger(GameControllerService.class);
    @Autowired
    GameRepository gameRepository;

    public StartResponseDto onStart() {
        StartResponseDto startResponseDto = new StartResponseDto();
        startResponseDto.setToken(getRandomToken());
        LOGGER.debug("Created Toke================= {}",startResponseDto.getToken());
        return startResponseDto;
    }

    private String getRandomToken() {
        String uuid = UUID.randomUUID().toString();
        GameRequestResponse gameRequestResponse = new GameRequestResponse();
        gameRequestResponse.setToken(uuid);
        gameRequestResponse.setState("Ready");
        gameRepository.save(gameRequestResponse);
        return uuid;
    }

    public GameResponseDto getServerResponse(String token, String clientResponse,String version) {
        GameRequestResponse gameRequestResponse = gameRepository.findByToken(token);
        LOGGER.debug("Database Response ============== {}",gameRequestResponse);
        if(gameRequestResponse==null || gameRequestResponse.getState().equals("Dead"))
            return null;
        GameRequestResponse saveGameRequestResponse = new GameRequestResponse();
        String serverMove = getServerMove(clientResponse,version);
        GameResponseDto gameResponseDto = new GameResponseDto();
        gameResponseDto.setServerMove(serverMove);
        getScore(gameRequestResponse, serverMove,clientResponse);
        gameResponseDto.setYourScore(gameRequestResponse.getYourScore());
        gameResponseDto.setServerScore(gameRequestResponse.getServerScore());

        if(gameResponseDto.getServerScore() >=3 || gameResponseDto.getYourScore()>=3)
            gameRequestResponse.setState("Dead");

        LOGGER.debug("Save Reesponse ====================== {}",gameRequestResponse);
        gameRepository.save(gameRequestResponse);
        return gameResponseDto;

    }

    private String getServerMove(String clientMove, String version) {

        if(version.equals("V1")) {
            Random rand = new Random();
            List<String> gameItems = Arrays.asList("rock", "paper", "scissors");
            int randomIndex = rand.nextInt(gameItems.size());
            String serverMove = gameItems.get(randomIndex);
            return serverMove;
        }
        if(clientMove.equals("rock"))
            return "paper";
        if(clientMove.equals("paper"))
            return "scissors";
        return "rock";
    }

    private void getScore(GameRequestResponse gameRequestResponse,String serverMove, String clientResponse) {
        if (clientResponse.equals(serverMove))
            System.out.println("Game is Tie !!");
            // if playerMove is ROCK
        else if (clientResponse.equals("rock")) {
            if (serverMove.equals("paper")) {
                gameRequestResponse.setServerScore(gameRequestResponse.getServerScore() + 1);
            } else {
                gameRequestResponse.setYourScore(gameRequestResponse.getYourScore() + 1);
            }
        }
            // if playerMove is PAPER
        else if (clientResponse.equals("paper")) {
            if (serverMove.equals("scissors")) {
                gameRequestResponse.setServerScore(gameRequestResponse.getServerScore() + 1);
            } else {
                gameRequestResponse.setYourScore(gameRequestResponse.getYourScore() + 1);
            }
        }
            // if playerMove is SCISSORS
        else {
            if (serverMove.equals("rock")) {
                gameRequestResponse.setServerScore(gameRequestResponse.getServerScore() + 1);
            } else {
                gameRequestResponse.setYourScore(gameRequestResponse.getYourScore() + 1);
            }
        }
    }
}
