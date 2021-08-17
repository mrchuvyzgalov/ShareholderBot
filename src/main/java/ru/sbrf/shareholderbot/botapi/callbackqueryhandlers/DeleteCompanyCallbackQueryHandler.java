package ru.sbrf.shareholderbot.botapi.callbackqueryhandlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.sbrf.shareholderbot.botapi.botstate.BotState;
import ru.sbrf.shareholderbot.cache.UserDataCache;
import ru.sbrf.shareholderbot.company.Company;

@AllArgsConstructor
@Component
public class DeleteCompanyCallbackQueryHandler extends CallbackQueryHandler {
    private UserDataCache userDataCache;

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        Company company = Company.valueOf(callbackQuery.getData().substring(6));

        if (userDataCache.getDeleteCompanySet().contains(company)) {
            userDataCache.getDeleteCompanySet().remove(company);
            userDataCache.getAddCompanySet().add(company);
            return sendAnswerCallbackQuery("Компания удалена из списка", true, callbackQuery);
        }
        else {
            return sendAnswerCallbackQuery("Данная компания уже остутствует в списке", true, callbackQuery);
        }
    }

    @Override
    public BotState getHandlerName() {
        return BotState.DELETE_COMPANY;
    }
}
