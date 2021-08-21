package ru.sbrf.shareholderbot.botapi.facade;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sbrf.shareholderbot.botapi.botstate.BotState;
import ru.sbrf.shareholderbot.botapi.botstate.BotStateMessageContext;
import ru.sbrf.shareholderbot.model.UserDataCache;

import java.util.concurrent.ExecutionException;


@AllArgsConstructor
@Component
public class InputMessageFacade {
    private UserDataCache userDataCache;
    private BotStateMessageContext botStateMessageContext;

    public SendMessage handleInputMessage(Message message) throws ExecutionException, InterruptedException {
        String inputMessage = message.getText();
        BotState botState;
        SendMessage replyMessage;

        switch(inputMessage){
            case "/start":
                botState = BotState.GREETING;
                break;
            case "Добавить компанию":
                botState = BotState.ADD_COMPANY;
                break;
            case "Удалить компанию":
                botState = BotState.DELETE_COMPANY;
                break;
            case "Посмотреть список компаний":
                botState = BotState.SHOW_COMPANY;
                break;
            case "Посмотреть цену акций всех компаний":
                botState = BotState.SHOW_SHARES;
                break;
            default:
                botState = BotState.SHOW_MENU;
                break;
        }

        userDataCache.setBotState(botState);

        replyMessage = botStateMessageContext.processInputMessage(botState, message);

        return replyMessage;
    }
}
