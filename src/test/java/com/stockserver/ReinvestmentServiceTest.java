package com.stockserver;

import com.stockserver.dividend.DividendEntity;
import com.stockserver.dividend.DividendService;
import com.stockserver.quote.QuoteEntity;
import com.stockserver.quote.QuoteService;
import com.stockserver.stock.StockEntity;
import com.stockserver.transaction.TransactionEntity;
import com.stockserver.transaction.TransactionService;
import com.stockserver.transaction.TransactionType;
import com.stockserver.transaction.ValidationStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ReinvestmentServiceTest {
    private final ReinvestmentService reinvestmentService;
    private final TransactionService transactionService;
    private final QuoteService quoteService;
    private final DividendService dividendService;

    public ReinvestmentServiceTest(@Mock TransactionService transactionService,
                                   @Mock QuoteService quoteService,
                                   @Mock DividendService dividendService) {
        this.transactionService = transactionService;
        this.quoteService = quoteService;
        this.dividendService = dividendService;
        this.reinvestmentService = new ReinvestmentService(transactionService, quoteService, dividendService);
    }

    @Test
    void reinvestTest() {
        String ticker = "SPY";
        var stock = StockEntity.builder().ticker(ticker).positionOpenDate(LocalDate.of(2022, 1, 15)).build();

        var transaction = TransactionEntity.builder()
                .ticker(ticker)
                .transactionDate(LocalDate.of(2022, 1, 15))
                .price(100)
                .amount(1000)
                .transactionType(TransactionType.PURCHASE)
                .validationStatus(ValidationStatus.VALIDATED)
                .build();
        Mockito.when(transactionService.getTransactionsOrderByTransactionDate(ticker))
                .thenReturn(List.of(transaction));


        var quotes = Map.of(LocalDate.of(2023, 1, 1), 400D,
                LocalDate.of(2024, 1, 1), 500D,
                LocalDate.of(2025, 1, 1), 600D);
        Mockito.when(quoteService.getQuotes(stock.getTicker(), stock.getPositionOpenDate()))
                .thenReturn(getMockQuotesData(ticker, quotes));

        var divs = Map.of(LocalDate.of(2023, 1, 1), 4D,
                LocalDate.of(2024, 1, 1), 5D,
                LocalDate.of(2025, 1, 1), 6D);
        Mockito.when(dividendService.getDividends(stock.getTicker(), stock.getPositionOpenDate()))
                .thenReturn(getMockDivData(ticker, divs));

        var transactions = reinvestmentService.generateReinvestmentTransactions(stock);

        Assertions.assertEquals(7, transactions.size());
        var totalQty = transactions.stream().mapToDouble(TransactionEntity::getQuantity).sum();
        Assertions.assertEquals(10.30301, totalQty);
    }

    @Test
    void reinvestReRunTest() {
        String ticker = "SPY";
        var stock = StockEntity.builder().ticker(ticker).positionOpenDate(LocalDate.of(2022, 1, 15)).build();

        var purchase = TransactionEntity.builder()
                .ticker(ticker)
                .transactionDate(LocalDate.of(2022, 1, 15))
                .price(100)
                .amount(1000)
                .transactionType(TransactionType.PURCHASE)
                .validationStatus(ValidationStatus.VALIDATED)
                .build();
        var cash = TransactionEntity.builder()
                .ticker(ticker)
                .transactionDate(LocalDate.of(2023, 1, 1))
                .amount(40D)
                .transactionType(TransactionType.DIVIDEND_CASH)
                .validationStatus(ValidationStatus.VALIDATED)
                .build();
        var reinv = TransactionEntity.builder()
                .ticker(ticker)
                .transactionDate(LocalDate.of(2023, 1, 1))
                .price(400)
                .amount(40D)
                .transactionType(TransactionType.REINVESTMENT)
                .validationStatus(ValidationStatus.VALIDATED)
                .build();
        Mockito.when(transactionService.getTransactionsOrderByTransactionDate(ticker))
                .thenReturn(List.of(purchase, cash, reinv));


        var quotes = Map.of(LocalDate.of(2023, 1, 1), 400D,
                LocalDate.of(2024, 1, 1), 500D,
                LocalDate.of(2025, 1, 1), 600D);
        Mockito.when(quoteService.getQuotes(stock.getTicker(), stock.getPositionOpenDate()))
                .thenReturn(getMockQuotesData(ticker, quotes));

        var divs = Map.of(LocalDate.of(2023, 1, 1), 4D,
                LocalDate.of(2024, 1, 1), 5D,
                LocalDate.of(2025, 1, 1), 6D);
        Mockito.when(dividendService.getDividends(stock.getTicker(), stock.getPositionOpenDate()))
                .thenReturn(getMockDivData(ticker, divs));

        var transactions = reinvestmentService.generateReinvestmentTransactions(stock);

        Assertions.assertEquals(7, transactions.size());
        var totalQty = transactions.stream().mapToDouble(TransactionEntity::getQuantity).sum();
        Assertions.assertEquals(10.30301, totalQty);
    }

    private List<QuoteEntity> getMockQuotesData(String ticker, Map<LocalDate, Double> quotes) {
        return quotes.entrySet()
                .stream()
                .map(x -> QuoteEntity.builder()
                        .open(x.getValue())
                        .ticker(ticker)
                        .quoteDate(x.getKey())
                        .build())
                .toList();
    }

    private List<DividendEntity> getMockDivData(String ticker, Map<LocalDate, Double> divs) {
        return divs.entrySet().stream()
                .map(x -> DividendEntity.builder()
                        .ticker(ticker)
                        .exDividendDate(x.getKey().minusDays(7))
                        .paymentDate(x.getKey())
                        .amount(x.getValue())
                        .build())
                .toList();
    }

}