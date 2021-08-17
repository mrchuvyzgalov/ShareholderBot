package ru.sbrf.shareholderbot.botapi.messagehandlers.messagewithbuttons;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.sbrf.shareholderbot.botapi.botstate.BotState;
import ru.sbrf.shareholderbot.cache.UserDataCache;
import ru.sbrf.shareholderbot.company.Company;
import ru.sbrf.shareholderbot.service.ReplyMessageService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class AddCompanyHandler extends MessageWithButtonsHandler {
    private ReplyMessageService replyMessageService;
    private UserDataCache userDataCache;

    @Override
    public SendMessage handle(Message message) {
        Set<Company> companies = userDataCache.getAddCompanySet();

        SendMessage sendMessage;
        if (companies.isEmpty()) {
            sendMessage = replyMessageService.getReplyMessage(message.getChatId(), "reply.add_company_none");
        }
        else {
            sendMessage = replyMessageService.getReplyMessage(message.getChatId(), "reply.add_company");
            sendMessage.setReplyMarkup(getInlineMessageButtons());
        }

        return sendMessage;
    }

    @Override
    protected InlineKeyboardMarkup getInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlineKeyboardButtons =
                userDataCache.getAddCompanySet().stream().map(company -> new InlineKeyboardButton()
                        .setText(company.getNameOfCompany()).setCallbackData("button" + company.toString()))
                        .collect(Collectors.toList());

        inlineKeyboardMarkup.setKeyboard(getRowList(inlineKeyboardButtons));
        return inlineKeyboardMarkup;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ADD_COMPANY;
    }
}
