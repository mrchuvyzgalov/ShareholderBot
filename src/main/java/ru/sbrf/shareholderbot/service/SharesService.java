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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SharesService {
    @Value("${tinkoffToken}")
    private String token;
    @Value("${isSandBoxMode}")
    private boolean isSandBoxMode;

    public Map<Company, String> getShares(List<Company> companies) {
        OpenApi api = new OkHttpOpenApi(token, isSandBoxMode);

        if (api.isSandboxMode()) {
            api.getSandboxContext().performRegistration(new SandboxRegisterRequest()).join();
        }

        Map<Company, String> companyStringMap = new HashMap<>();
        for (Company company : companies) {
            List<Candle> candles = api.getMarketContext().getMarketCandles(company.getFigi(),
                    OffsetDateTime.of(LocalDateTime.from(LocalDateTime.now().minusHours(24)), ZoneOffset.UTC),
                    OffsetDateTime.of(LocalDateTime.from(LocalDateTime.now()), ZoneOffset.UTC),
                    CandleResolution._1MIN).join().get().getCandles();

            if (candles.isEmpty()) {
                companyStringMap.put(company, "Нет информации");
            }
            else {
                companyStringMap.put(company, Double.toString(candles.get(candles.size() - 1).getC().doubleValue()));
            }
        }

        return companyStringMap;
    }

    public String getShares(Company company) {
        OpenApi api = new OkHttpOpenApi(token, isSandBoxMode);

        if (api.isSandboxMode()) {
            api.getSandboxContext().performRegistration(new SandboxRegisterRequest()).join();
        }

        List<Candle> candles = api.getMarketContext().getMarketCandles(company.getFigi(),
                OffsetDateTime.of(LocalDateTime.from(LocalDateTime.now().minusHours(24)), ZoneOffset.UTC),
                OffsetDateTime.of(LocalDateTime.from(LocalDateTime.now()), ZoneOffset.UTC),
                CandleResolution._1MIN).join().get().getCandles();

        if (candles.isEmpty()) {
            return "Нет информации";
        }
        else {
            return Double.toString(candles.get(candles.size() - 1).getC().doubleValue());
        }
    }
}
