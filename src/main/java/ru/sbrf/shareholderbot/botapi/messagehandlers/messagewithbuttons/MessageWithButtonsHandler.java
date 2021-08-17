package ru.sbrf.shareholderbot.botapi.messagehandlers.messagewithbuttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.sbrf.shareholderbot.botapi.messagehandlers.InputMessageHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class MessageWithButtonsHandler implements InputMessageHandler {
    protected abstract InlineKeyboardMarkup getInlineMessageButtons();

    protected List<List<InlineKeyboardButton>> getRowList(List<InlineKeyboardButton> inlineKeyboardButtons) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (int i = 0; i < inlineKeyboardButtons.size(); ++i) {
            if (i % 2 == 0) {
                rowList.add(new ArrayList<>());
            }

            rowList.get(i / 2).add(inlineKeyboardButtons.get(i));
        }
        return rowList;
    }
}
