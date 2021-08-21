package ru.sbrf.shareholderbot.botapi.callbackqueryhandlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.sbrf.shareholderbot.botapi.botstate.BotState;

public interface CallbackQueryHandler {
    BotApiMethod<?> handle(CallbackQuery callbackQuery);

    BotState getHandlerName();
}
