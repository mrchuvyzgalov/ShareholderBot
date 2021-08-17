package ru.sbrf.shareholderbot.botapi.facade;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.sbrf.shareholderbot.botapi.botstate.BotStateCallbackQueryContext;
import ru.sbrf.shareholderbot.cache.UserDataCache;

@AllArgsConstructor
@Component
public class CallbackQueryFacade {
    private UserDataCache userDataCache;
    private BotStateCallbackQueryContext botStateCallbackQueryContext;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        return botStateCallbackQueryContext.processCallbackQuery(userDataCache.getBotState(), callbackQuery);
    }
}
