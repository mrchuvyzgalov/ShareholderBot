package ru.sbrf.shareholderbot.botapi.messagehandlers.message;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sbrf.shareholderbot.botapi.botstate.BotState;
import ru.sbrf.shareholderbot.botapi.messagehandlers.InputMessageHandler;
import ru.sbrf.shareholderbot.service.LocaleMessageService;
import ru.sbrf.shareholderbot.service.MainMenuService;

@AllArgsConstructor
@Component
public class ShowMenuHandler implements InputMessageHandler {

    private MainMenuService menuService;
    private LocaleMessageService localeMessageService;

    @Override
    public SendMessage handle(Message message) {
        return menuService.getMainMenuMessage(message.getChatId(), localeMessageService.getMessage("reply.use_main_menu"));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_MENU;
    }
}
