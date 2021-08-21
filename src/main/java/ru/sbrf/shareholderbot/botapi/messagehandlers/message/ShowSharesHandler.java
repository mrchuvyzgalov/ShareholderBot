package ru.sbrf.shareholderbot.botapi.messagehandlers.message;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sbrf.shareholderbot.botapi.botstate.BotState;
import ru.sbrf.shareholderbot.botapi.messagehandlers.InputMessageHandler;
import ru.sbrf.shareholderbot.company.Company;
import ru.sbrf.shareholderbot.model.UserDataCache;
import ru.sbrf.shareholderbot.service.SharesService;
import ru.sbrf.shareholderbot.service.ReplyMessageService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@AllArgsConstructor
@Component
public class ShowSharesHandler implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessageService replyMessageService;
    private SharesService sharesService;

    @Override
    public SendMessage handle(Message message) throws ExecutionException, InterruptedException {
        List<Company> companyList = userDataCache.getDeleteCompanyList();

        SendMessage sendMessage;
        if (companyList.isEmpty()) {
            sendMessage = replyMessageService.getReplyMessage(message.getChatId(), "reply.show_shares_none");
        }
        else {
            sendMessage = replyMessageService.getReplyMessage(message.getChatId(), "reply.show_shares");

            Map<Company, String> shares = sharesService.getShares(companyList);

            for (var entry : shares.entrySet()) {
                if (entry.getValue() != "Нет информации") {
                    userDataCache.getLastPrice().put(entry.getKey(), Double.parseDouble(entry.getValue()));
                }
            }

            StringBuilder companyText = new StringBuilder();
            shares.entrySet()
                    .stream()
                    .forEach(entry -> companyText.append("\n" + entry.getKey().getNameOfCompany() + ": " + entry.getValue()));

            sendMessage.setText(sendMessage.getText() + "\n" + companyText);
        }

        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_SHARES;
    }
}
