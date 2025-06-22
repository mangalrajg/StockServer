package com.stockserver;

import com.stockserver.dividend.DividendService;
import com.stockserver.market_data.MarketDataLoadingService;
import com.stockserver.quote.QuoteService;
import com.stockserver.stock.StockEntity;
import com.stockserver.stock.StockService;
import com.stockserver.transaction.TransactionEntity;
import com.stockserver.transaction.TransactionService;
import com.stockserver.transaction.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
@Disabled
class LoadQuotesTest {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private StockService stockService;
    @Autowired
    private QuoteService quoteService;

    @Autowired
    private DividendService dividendService;

    @Autowired
    private MarketDataLoadingService loadingService;

    @Test
    void downloadQuotes() {
        // Initial setup

        // 1. Stock
        String ticker = "SPY";
        var stock = StockEntity.builder().ticker(ticker).positionOpenDate(LocalDate.of(2022, 1, 15)).build();
        var savedStocks = stockService.getStocks();
        if (!savedStocks.contains(stock))
            stockService.saveStocks(List.of(stock));

        // 2. Transaction
        var savedTransactions = transactionService.getTransactionsOrderByTransactionDate(ticker);
        var transaction = TransactionEntity.builder()
                .ticker(ticker)
                .transactionDate(LocalDate.of(2022, 1, 15))
                .price(100D)
                .amount(2000D)
                .transactionType(TransactionType.BUY)
                .build();
        if (!savedTransactions.contains(transaction))
            transactionService.saveTransactions(List.of(transaction));

        var savedQuotes = loadingService.loadQuoteData(ticker);
        var savedDivs = loadingService.loadDivData(ticker);
        Assertions.assertNotNull(savedQuotes);
        Assertions.assertNotNull(savedDivs);

        // Checks
        var quotes = quoteService.getQuotes(ticker);
        var divs = dividendService.getDividends(stock.getTicker(), stock.getPositionOpenDate());

        Assertions.assertNotNull(quotes);
        Assertions.assertNotNull(divs);

    }

    @Test
    void saveTransactions() {
    }
}