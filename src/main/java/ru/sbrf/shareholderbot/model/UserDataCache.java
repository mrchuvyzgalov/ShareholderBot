package ru.sbrf.shareholderbot.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.sbrf.shareholderbot.botapi.botstate.BotState;
import ru.sbrf.shareholderbot.company.Company;

import java.util.*;

@Setter
@Getter
@Component
public class UserDataCache {
    private String chatId = "";
    private BotState botState;
    private List<Company> companyList = new ArrayList<>();
    private Map<Company, Double> lastPrice = new HashMap<>();
}
