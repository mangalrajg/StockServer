package com.stockserver.infrastucture.alpha_vantage;

import com.stockserver.dividend.DividendEntity;
import com.stockserver.market_data.MarketDividendService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlphaVantageService implements MarketDividendService {
    @Value("${stock-server.alpha_vantage.api_key}")
    private String API_KEY;
    private final AlphaVantageConnector connector;
    private final DividentMapper mapper;

    public List<DividendEntity> getDividends(String ticker, LocalDate startDate) {
        var divs = connector.getDividends(ticker, API_KEY);
        var divEntities = divs.data().stream()
                .filter(x -> x.exDividendDate().isAfter(startDate))
                .map(x -> mapper.map(ticker, removeNone(x)))
                .toList();
        return divEntities;
    }

    private DividendEntryDto removeNone(DividendEntryDto x) {
        return DividendEntryDto.builder()
                .exDividendDate(x.exDividendDate())
                .declarationDate(clean(x.declarationDate()))
                .recordDate(clean(x.recordDate()))
                .paymentDate(clean(x.paymentDate()))
                .amount(x.amount())
                .build();
    }

    private String clean(String s) {
        if ("None".equals(s))
            return null;

        return s;
    }
}
