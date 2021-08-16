package ru.sbrf.shareholderbot.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import ru.sbrf.shareholderbot.ShareholderTelegramBot;

@Getter
@Setter
@Configuration
public class BotConfiguration {
    @Value("${telegrambot.webHookPath}")
    private String webHookPath;
    @Value("${telegrambot.userName}")
    private String userName;
    @Value("${telegrambot.botToken}")
    private String botToken;

    @Bean
    public ShareholderTelegramBot shareholderTelegramBot() {
        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);

        ShareholderTelegramBot shareholderTelegramBot = new ShareholderTelegramBot(options);
        shareholderTelegramBot.setBotToken(webHookPath);
        shareholderTelegramBot.setBotUserName(userName);
        shareholderTelegramBot.setWebHookPath(webHookPath);

        return shareholderTelegramBot;
    }
}
