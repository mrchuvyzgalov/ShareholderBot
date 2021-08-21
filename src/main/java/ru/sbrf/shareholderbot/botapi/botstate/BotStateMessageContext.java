package ru.sbrf.shareholderbot.botapi.botstate;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sbrf.shareholderbot.botapi.messagehandlers.InputMessageHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
public class BotStateMessageContext {
    private Map<BotState, InputMessageHandler> messageHandler = new HashMap<>();

    public BotStateMessageContext(List<InputMessageHandler> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandler.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotState botState, Message message) throws ExecutionException, InterruptedException {
        SendMessage replyMessage = messageHandler.get(botState).handle(message);

        return replyMessage;
    }
}
