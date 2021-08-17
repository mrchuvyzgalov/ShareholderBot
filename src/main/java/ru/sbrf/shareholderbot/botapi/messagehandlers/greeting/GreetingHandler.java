package ru.sbrf.shareholderbot.botapi.messagehandlers.greeting;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sbrf.shareholderbot.botapi.botstate.BotState;
import ru.sbrf.shareholderbot.botapi.messagehandlers.InputMessageHandler;
import ru.sbrf.shareholderbot.botapi.messagehandlers.messagewithbuttons.AddCompanyHandler;
import ru.sbrf.shareholderbot.cache.UserDataCache;
import ru.sbrf.shareholderbot.service.MainMenuService;
import ru.sbrf.shareholderbot.service.ReplyMessageService;

@AllArgsConstructor
@Component
public class GreetingHandler implements InputMessageHandler {
    private ReplyMessageService replyMessageService;
    private UserDataCache userDataCache;
    private MainMenuService menuService;

    @Override
    public SendMessage handle(Message message) {
        userDataCache.setBotState(BotState.SHOW_COMPANY);

        SendMessage replyFromMessageService = replyMessageService.getReplyMessage(message.getChatId(), "reply.greeting");
        SendMessage replyFromMainMenu = menuService.getMainMenuMessage(message.getChatId(), "Чтобы взаимодействовать со мной, пользуйтесь главным меню");

        String replyMessage = replyFromMessageService.getText() + "\n" + replyFromMainMenu.getText();
        replyFromMainMenu.setText(replyMessage);

        return replyFromMainMenu;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.GREETING;
    }
}
