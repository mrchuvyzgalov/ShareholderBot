package ru.sbrf.shareholderbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShareholderBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShareholderBotApplication.class, args);
    }

}
