package ru.sbrf.shareholderbot.botapi.facade;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
@Component
@Slf4j
public class TelegramFacade {
    private CallbackQueryFacade callbackQueryFacade;
    private InputMessageFacade inputMessageFacade;

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, childId: {}, with text: {}",
                    callbackQuery.getFrom().getUserName(), callbackQuery.getMessage().getChatId(), callbackQuery.getMessage());
            return callbackQueryFacade.processCallbackQuery(callbackQuery);
        }

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User: {}, chatId: {}, with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyMessage = inputMessageFacade.handleInputMessage(message);
        }

        return replyMessage;
    }
}
