package ru.sbrf.shareholderbot.botapi.callbackqueryhandlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.sbrf.shareholderbot.botapi.botstate.BotState;
import ru.sbrf.shareholderbot.model.UserDataCache;
import ru.sbrf.shareholderbot.company.Company;
import ru.sbrf.shareholderbot.service.SharesService;
import ru.sbrf.shareholderbot.service.ReplyMessageService;

@AllArgsConstructor
@Component
public class AddCompanyCallbackQueryHandler implements CallbackQueryHandler {
    private ReplyMessageService replyMessageService;
    private UserDataCache userDataCache;
    private SharesService sharesService;

    @Override
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        Company company = Company.valueOf(callbackQuery.getData().substring(6));

        if (!userDataCache.getDeleteCompanyList().contains(company)) {
            userDataCache.getDeleteCompanyList().add(company);
            userDataCache.getAddCompanyList().remove(company);

            String price = sharesService.getShares(company);

            SendMessage answerMessage = replyMessageService.getReplyMessage(callbackQuery.getMessage().getChatId(), "reply.company_was_added");

            if (price != "Нет информации") {
                userDataCache.getLastPrice().put(company, Double.parseDouble(price));

                SendMessage addition = replyMessageService.getReplyMessage(callbackQuery.getMessage().getChatId(), "reply.show_share");
                answerMessage.setText(answerMessage.getText() + "\n\n" + addition.getText() + " " + company.getNameOfCompany() + ": " + price);
            }
            else {
                userDataCache.getLastPrice().put(company, -1D);

                SendMessage addition = replyMessageService.getReplyMessage(callbackQuery.getMessage().getChatId(), "reply.show_share_none");
                answerMessage.setText(answerMessage.getText() + "\n\n" + addition.getText() + " " + company.getNameOfCompany());
            }

            return answerMessage;
        }
        else {
            return replyMessageService.getReplyMessage(callbackQuery.getMessage().getChatId(), "reply.company_was_not_added");
        }
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ADD_COMPANY;
    }
}
