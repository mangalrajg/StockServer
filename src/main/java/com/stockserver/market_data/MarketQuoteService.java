package com.stockserver.market_data;

import com.stockserver.quote.QuoteEntity;

import java.time.LocalDate;
import java.util.List;

public interface MarketQuoteService {
    List<QuoteEntity> getPrices(String ticker, LocalDate startDate, LocalDate endDate);
}
