package com.stockserver.market_data;

import com.stockserver.dividend.DividendEntity;
import com.stockserver.dividend.DividendService;
import com.stockserver.quote.QuoteEntity;
import com.stockserver.quote.QuoteService;
import com.stockserver.transaction.TransactionEntity;
import com.stockserver.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MarketDataLoadingService {
    private final MarketDividendService marketDividendService;
    private final MarketQuoteService mkdDataService;

    private final DividendService dividendService;
    private final TransactionService transactionService;
    private final QuoteService quoteService;

    private LocalDate getPositionOpenDate(String ticker) {
        var t = transactionService.getTransactionsOrderByTransactionDate(ticker);
        return t.stream()
                .map(TransactionEntity::getTransactionDate)
                .min(LocalDate::compareTo)
                .orElse(LocalDate.now());
    }

    public List<QuoteEntity> loadQuoteData(String ticker) {
        var posOpenDate = getPositionOpenDate(ticker);
        var availableQuotes = quoteService.getQuotes(ticker);
        var latestQuoteDate = availableQuotes.stream()
                .map(QuoteEntity::getQuoteDate)
                .max(LocalDate::compareTo)
                .orElse(LocalDate.MIN);

        var quoteStartDate = posOpenDate.isAfter(latestQuoteDate) ? posOpenDate : latestQuoteDate;
        var marketData = mkdDataService.getPrices(ticker, quoteStartDate, LocalDate.now());
        return quoteService.saveQuotes(marketData);
    }

    public List<DividendEntity> loadDivData(String ticker) {
        var posOpenDate = getPositionOpenDate(ticker);
        var d = dividendService.getDividends(ticker, posOpenDate);
        var latestDivDate = d.stream()
                .map(DividendEntity::getExDividendDate)
                .max(LocalDate::compareTo)
                .orElse(LocalDate.MIN);
        var divStartDate = posOpenDate.isAfter(latestDivDate) ? posOpenDate : latestDivDate;
        var marketDivs = marketDividendService.getDividends(ticker, divStartDate);
        return dividendService.saveDividends(marketDivs);
    }
}
