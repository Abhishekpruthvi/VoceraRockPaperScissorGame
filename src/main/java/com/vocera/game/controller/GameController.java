package com.vocera.game.controller;

import com.vocera.game.dto.GameResponseDto;
import com.vocera.game.service.GameControllerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vocera")
public class GameController {

    private static final Logger LOGGER = LogManager.getLogger(GameController.class);

    @Autowired
    GameControllerService gameControllerService;

    @GetMapping("/start")
    public @ResponseBody ResponseEntity<?> startGame() {
        LOGGER.debug("Entered Start Game =============================");
        return new ResponseEntity<>(gameControllerService.onStart(), HttpStatus.OK);
    }

    @GetMapping("/v1/{token}/{clientResponse}")
    public @ResponseBody ResponseEntity<?> ClientAction(@PathVariable String token,@PathVariable String clientResponse) {
        LOGGER.debug("User API Call ============================== {}",clientResponse,token);
        if(!(clientResponse.equals("rock") || clientResponse.equals("paper") || clientResponse.equals("scissors")))
            return new ResponseEntity<>("API Path should be /rock or /paper or /scissors", HttpStatus.BAD_REQUEST);
        GameResponseDto gameResponseDto = gameControllerService.getServerResponse(token,clientResponse,"V1");
        if(gameResponseDto==null)   {
            return  new ResponseEntity<>("Please Start the Game First", HttpStatus.BAD_REQUEST);
        }
        if(gameResponseDto.getYourScore()>=3)
            return new ResponseEntity<>("CONGRAGTULATIONS!!! YOU WON THE GAME. PLEASE START NEW GAME TO CONTINUE...",HttpStatus.OK);
        if(gameResponseDto.getServerScore()>=3)
            return new ResponseEntity<>("YOU LOST THE GAME. PLEASE START A NEW GAME TO CONTINUE",HttpStatus.OK);
        return new ResponseEntity<>(gameResponseDto,HttpStatus.OK);

    }
    @GetMapping("/v2/{token}/{clientResponse}")
    public @ResponseBody ResponseEntity<?> ClientActionV2(@PathVariable String token,@PathVariable String clientResponse) {
        LOGGER.debug("User API Call ============================== {}",clientResponse,token);
        if(!(clientResponse.equals("rock") || clientResponse.equals("paper") || clientResponse.equals("scissors")))
            return new ResponseEntity<>("API Path should be /rock or /paper or /scissors", HttpStatus.BAD_REQUEST);
        GameResponseDto gameResponseDto = gameControllerService.getServerResponse(token,clientResponse,"V2");
        if(gameResponseDto==null)   {
            return  new ResponseEntity<>("Please Start the Game First", HttpStatus.BAD_REQUEST);
        }
        if(gameResponseDto.getYourScore()>=3)
            return new ResponseEntity<>("CONGRAGTULATIONS!!! YOU WON THE GAME. PLEASE START NEW GAME TO CONTINUE...",HttpStatus.OK);
        if(gameResponseDto.getServerScore()>=3)
            return new ResponseEntity<>("YOU LOST THE GAME. PLEASE START A NEW GAME TO CONTINUE",HttpStatus.OK);
        return new ResponseEntity<>(gameResponseDto,HttpStatus.OK);

    }
}
