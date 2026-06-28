package com.example.otclients.bootstrap;

import com.example.otclients.services.command.configuration.CountryCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
@RequiredArgsConstructor
@Slf4j
public class Bootstrap implements ApplicationRunner {

    private final CountryCommandService countryCommandService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            countryCommandService.bootstrap();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
