package ru.sbrf.shareholderbot.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sbrf.shareholderbot.company.Company;

import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.model.rest.Candle;
import ru.tinkoff.invest.openapi.model.rest.CandleResolution;
import ru.tinkoff.invest.openapi.model.rest.SandboxRegisterRequest;
import ru.tinkoff.invest.openapi.okhttp.OkHttpOpenApi;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class SharesService {
    @Value("${tinkoffToken}")
    private String token;
    @Value("${isSandBoxMode}")
    private boolean isSandBoxMode;

    private LocaleMessageService localeMessageService;

    public SharesService(LocaleMessageService localeMessageService) {
        this.localeMessageService = localeMessageService;
    }

    public Map<Company, String> getShares(List<Company> companies) {
        OpenApi api = new OkHttpOpenApi(token, isSandBoxMode);

        if (api.isSandboxMode()) {
            api.getSandboxContext().performRegistration(new SandboxRegisterRequest()).join();
        }

        Map<Company, String> companyStringMap = new HashMap<>();
        for (Company company : companies) {
            try {
                List<Candle> candles = api.getMarketContext().getMarketCandles(company.getFigi(),
                        OffsetDateTime.of(LocalDateTime.from(LocalDateTime.now().minusDays(4)), ZoneOffset.UTC),
                        OffsetDateTime.of(LocalDateTime.from(LocalDateTime.now()), ZoneOffset.UTC),
                        CandleResolution.HOUR).join().get().getCandles();

                if (candles.isEmpty()) {
                    throw new Exception();
                } else {
                    companyStringMap.put(company, Double.toString(candles.get(candles.size() - 1).getC().doubleValue()));
                }
            }
            catch (Exception e) {
                companyStringMap.put(company, localeMessageService.getMessage("reply.no_info"));
            }
        }

        return companyStringMap;
    }

    public String getShares(Company company) {
        return getShares(new ArrayList<>(Arrays.asList(company))).get(company);
    }
}
