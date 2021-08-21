package ru.sbrf.shareholderbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sbrf.shareholderbot.botapi.ShareholderTelegramBot;
import ru.sbrf.shareholderbot.company.Company;
import ru.sbrf.shareholderbot.model.UserDataCache;

import java.util.Map;

@Slf4j
@Service
public class SubscriptionService {
    @Value("${tinkoffToken}")
    private String token;
    @Value("${isSandBoxMode}")
    private boolean isSandBoxMode;
    @Value("${persent}")
    private Double persent;

    private ShareholderTelegramBot telegramBot;
    private UserDataCache userDataCache;
    private SharesService sharesService;
    private ReplyMessageService replyMessageService;

    public SubscriptionService(UserDataCache userDataCache, SharesService sharesService, ShareholderTelegramBot telegramBot) {
        this.userDataCache = userDataCache;
        this.sharesService = sharesService;
        this.telegramBot = telegramBot;
    }

    @Scheduled(fixedRateString = "${subscriptions.processPeriod}")
    public void processSubscription() {
        log.info("Start of subscription process for chatId: {}", userDataCache.getChatId());

        Map<Company, String> companyStringMap = sharesService.getShares(userDataCache.getDeleteCompanyList());

        for (var entry : companyStringMap.entrySet()) {
            if (entry.getValue() != "Нет информации") {
                Double tmpPrice = Double.parseDouble(entry.getValue());
                Double lastPrice = userDataCache.getLastPrice().get(entry.getKey());

                if (lastPrice < 0) {
                    SendMessage answerMessage = replyMessageService.getReplyMessage(Long.parseLong(userDataCache.getChatId()), "reply.subscription_start");
                    answerMessage.setText(answerMessage.getText() + " " + entry.getKey().getNameOfCompany() + ": " + entry.getValue());
                    telegramBot.sendMessage(answerMessage);
                }
                else if (Math.abs(tmpPrice - lastPrice) >= lastPrice * persent / 100) {
                    SendMessage answerMessage = replyMessageService.getReplyMessage(Long.parseLong(userDataCache.getChatId()), "reply.subscription");
                    answerMessage.setText(answerMessage.getText() + " " + entry.getKey().getNameOfCompany() + ": " + entry.getValue());
                    telegramBot.sendMessage(answerMessage);
                }

                userDataCache.getLastPrice().put(entry.getKey(), tmpPrice);
            }
        }

        log.info("End of subscription process for chatId: {}", userDataCache.getChatId());
    }
}
