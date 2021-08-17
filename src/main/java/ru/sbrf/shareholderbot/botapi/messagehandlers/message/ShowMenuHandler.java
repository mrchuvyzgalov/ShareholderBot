package ru.sbrf.shareholderbot.botapi.messagehandlers.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sbrf.shareholderbot.botapi.botstate.BotState;
import ru.sbrf.shareholderbot.botapi.messagehandlers.InputMessageHandler;
import ru.sbrf.shareholderbot.service.MainMenuService;

@Component
public class ShowMenuHandler implements InputMessageHandler {

    private MainMenuService menuService;

    public ShowMenuHandler(MainMenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public SendMessage handle(Message message) {
        return menuService.getMainMenuMessage(message.getChatId(), "Воспользуйтесь главным меню");
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_MENU;
    }
}
