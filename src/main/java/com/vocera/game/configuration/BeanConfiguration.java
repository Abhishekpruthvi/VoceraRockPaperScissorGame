package com.vocera.game.configuration;

import com.vocera.game.service.GameControllerService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfiguration {
    @Bean
    public GameControllerService getGameControllerService() {
        return new GameControllerService();
    }

}
