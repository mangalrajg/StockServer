package com.stockserver.infrastucture.tiingo;

import com.stockserver.market_data.MarketQuoteService;
import com.stockserver.quote.QuoteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TiingoService implements MarketQuoteService {

    @Value("${stock-server.tiingo.api_key}")
    private String API_KEY;
    private final TiingoConnector connector;
    private final QuoteMapper mapper;

    public Object getMetaData(String ticker) {
        return connector.getMetaData(ticker, API_KEY);
    }

    public List<QuoteEntity> getPrices(String ticker, LocalDate startDate, LocalDate endDate) {
        var quotes = connector.getPrices(ticker, startDate, endDate, API_KEY);
        return quotes.stream().map(x -> mapper.map(ticker, x)).toList();
    }


}
