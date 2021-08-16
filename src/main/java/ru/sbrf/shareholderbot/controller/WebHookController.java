package ru.sbrf.shareholderbot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sbrf.shareholderbot.ShareholderTelegramBot;

@Slf4j
@RestController
public class WebHookController {
    private final ShareholderTelegramBot shareholderTelegramBot;

    public WebHookController(ShareholderTelegramBot shareholderTelegramBot) {
        this.shareholderTelegramBot = shareholderTelegramBot;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return shareholderTelegramBot.onWebhookUpdateReceived(update);
    }
}
