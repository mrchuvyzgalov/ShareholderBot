package ru.sbrf.shareholderbot.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import ru.sbrf.shareholderbot.botapi.ShareholderTelegramBot;
import ru.sbrf.shareholderbot.botapi.facade.TelegramFacade;

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
    public ShareholderTelegramBot shareholderTelegramBot(TelegramFacade telegramFacade) {
        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);

        ShareholderTelegramBot shareholderTelegramBot = new ShareholderTelegramBot(options, telegramFacade);
        shareholderTelegramBot.setBotToken(webHookPath);
        shareholderTelegramBot.setBotUserName(userName);
        shareholderTelegramBot.setWebHookPath(webHookPath);

        return shareholderTelegramBot;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
