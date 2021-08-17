package ru.sbrf.shareholderbot.botapi.messagehandlers.message;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sbrf.shareholderbot.botapi.botstate.BotState;
import ru.sbrf.shareholderbot.botapi.messagehandlers.InputMessageHandler;
import ru.sbrf.shareholderbot.cache.UserDataCache;
import ru.sbrf.shareholderbot.company.Company;
import ru.sbrf.shareholderbot.service.ReplyMessageService;

import java.util.Set;

@AllArgsConstructor
@Component
public class ShowCompanyHandler implements InputMessageHandler {
    private ReplyMessageService replyMessageService;
    private UserDataCache userDataCache;

    @Override
    public SendMessage handle(Message message) {
        userDataCache.setBotState(BotState.SHOW_MENU);

        Set<Company> companySet = userDataCache.getDeleteCompanySet();

        SendMessage sendMessage;

        if (companySet.isEmpty()) {
            sendMessage = replyMessageService.getReplyMessage(message.getChatId(), "reply.show_company_none");
        }
        else {
            sendMessage = replyMessageService.getReplyMessage(message.getChatId(), "reply.show_company");
            StringBuilder text = new StringBuilder(sendMessage.getText());
            text.append("\n");

            companySet.stream().forEach(company -> text.append("\n" + company.getNameOfCompany()));

            sendMessage.setText(text.toString());
        }

        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_COMPANY;
    }
}
