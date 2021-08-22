package ru.sbrf.shareholderbot.botapi.facade;

import lombok.AllArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sbrf.shareholderbot.botapi.botstate.BotState;
import ru.sbrf.shareholderbot.botapi.botstate.BotStateMessageContext;
import ru.sbrf.shareholderbot.model.UserDataCache;
import ru.sbrf.shareholderbot.service.LocaleMessageService;

import java.util.concurrent.ExecutionException;


@AllArgsConstructor
@Component
public class InputMessageFacade {
    private UserDataCache userDataCache;
    private BotStateMessageContext botStateMessageContext;
    private LocaleMessageService localeMessageService;

    public SendMessage handleInputMessage(Message message) throws ExecutionException, InterruptedException {
        String inputMessage = message.getText();
        BotState botState;
        SendMessage replyMessage;

        if (inputMessage.equals("/start")) {
            botState = BotState.GREETING;
        }
        else if (inputMessage.equals(localeMessageService.getMessage("button.add_company"))) {
            botState = BotState.ADD_COMPANY;
        }
        else if (inputMessage.equals(localeMessageService.getMessage("button.delete_company"))) {
            botState = BotState.DELETE_COMPANY;
        }
        else if (inputMessage.equals(localeMessageService.getMessage("button.show_company"))) {
            botState = BotState.SHOW_COMPANY;
        }
        else if (inputMessage.equals(localeMessageService.getMessage("button.show_shares"))) {
            botState = BotState.SHOW_SHARES;
        }
        else {
            botState = BotState.SHOW_MENU;
        }

        userDataCache.setBotState(botState);

        replyMessage = botStateMessageContext.processInputMessage(botState, message);

        return replyMessage;
    }
}
