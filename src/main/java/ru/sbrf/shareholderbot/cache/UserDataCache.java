package ru.sbrf.shareholderbot.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.sbrf.shareholderbot.botapi.botstate.BotState;
import ru.sbrf.shareholderbot.company.Company;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Component
public class UserDataCache {
    private BotState botState;
    private Set<Company> deleteCompanySet = new HashSet<>();
    private Set<Company> addCompanySet = new HashSet<>();

    public UserDataCache() {
        Arrays.stream(Company.values())
                .filter(company -> !deleteCompanySet.contains(company))
                .forEach(company -> addCompanySet.add(company));
    }
}
