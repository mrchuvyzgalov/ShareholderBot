package ru.sbrf.shareholderbot.botapi.messagehandlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sbrf.shareholderbot.botapi.botstate.BotState;

import java.util.concurrent.ExecutionException;

public interface InputMessageHandler {
    SendMessage handle(Message message) throws ExecutionException, InterruptedException;

    BotState getHandlerName();
}
