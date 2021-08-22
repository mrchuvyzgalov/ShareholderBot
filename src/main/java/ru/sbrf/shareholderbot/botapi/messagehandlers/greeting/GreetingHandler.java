package ru.sbrf.shareholderbot.botapi.messagehandlers.greeting;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sbrf.shareholderbot.botapi.botstate.BotState;
import ru.sbrf.shareholderbot.botapi.messagehandlers.InputMessageHandler;
import ru.sbrf.shareholderbot.model.UserDataCache;
import ru.sbrf.shareholderbot.service.LocaleMessageService;
import ru.sbrf.shareholderbot.service.MainMenuService;
import ru.sbrf.shareholderbot.service.ReplyMessageService;

@AllArgsConstructor
@Component
public class GreetingHandler implements InputMessageHandler {
    private LocaleMessageService localeMessageService;
    private UserDataCache userDataCache;
    private MainMenuService menuService;

    @Override
    public SendMessage handle(Message message) {
        userDataCache.setBotState(BotState.SHOW_COMPANY);
        userDataCache.setChatId(message.getChatId().toString());

        return menuService.getMainMenuMessage(message.getChatId(), localeMessageService.getMessage("reply.greeting"));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.GREETING;
    }
}
