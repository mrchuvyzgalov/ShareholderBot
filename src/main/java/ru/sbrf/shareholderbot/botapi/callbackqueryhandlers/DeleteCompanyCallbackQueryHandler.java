package ru.sbrf.shareholderbot.botapi.callbackqueryhandlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.sbrf.shareholderbot.botapi.botstate.BotState;
import ru.sbrf.shareholderbot.model.UserDataCache;
import ru.sbrf.shareholderbot.company.Company;
import ru.sbrf.shareholderbot.service.ReplyMessageService;

@AllArgsConstructor
@Component
public class DeleteCompanyCallbackQueryHandler implements CallbackQueryHandler {
    private ReplyMessageService replyMessageService;
    private UserDataCache userDataCache;

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        Company company = Company.valueOf(callbackQuery.getData().substring(6));

        if (userDataCache.getCompanyList().contains(company)) {
            userDataCache.getCompanyList().remove(company);
            userDataCache.getLastPrice().remove(company);
            return replyMessageService.getReplyMessage(callbackQuery.getMessage().getChatId(), "reply.company_was_deleted");
        }
        else {
            return replyMessageService.getReplyMessage(callbackQuery.getMessage().getChatId(), "reply.company_was_not_deleted");
        }
    }

    @Override
    public BotState getHandlerName() {
        return BotState.DELETE_COMPANY;
    }
}
