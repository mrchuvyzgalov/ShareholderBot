package ru.sbrf.shareholderbot.botapi.botstate;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.sbrf.shareholderbot.botapi.callbackqueryhandlers.CallbackQueryHandler;
import ru.sbrf.shareholderbot.service.MainMenuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateCallbackQueryContext {
    private MainMenuService menuService;
    private Map<BotState, CallbackQueryHandler> handlers = new HashMap<>();

    public BotStateCallbackQueryContext(List<CallbackQueryHandler> callbackQueryHandlers, MainMenuService menuService) {
        callbackQueryHandlers.forEach(handler -> handlers.put(handler.getHandlerName(), handler));
        this.menuService = menuService;
    }

    public BotApiMethod<?> processCallbackQuery(BotState botState, CallbackQuery callbackQuery) {
        if (handlers.containsKey(botState)) {
            return handlers.get(botState).handle(callbackQuery);
        }
        return menuService.getMainMenuMessage(callbackQuery.getMessage().getChatId(), "Воспользуйтесь главным меню");
    }
}
