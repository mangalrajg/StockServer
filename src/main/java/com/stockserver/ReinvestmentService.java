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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReinvestmentService {
    private final TransactionService transactionService;
    private final QuoteService quoteService;
    private final DividendService dividendService;

    private List<TransactionEntity> getReinvestmentTransactions(String ticker,
                                                                List<QuoteEntity> quotes,
                                                                List<TransactionEntity> transactions,
                                                                List<DividendEntity> dividends) {
        var quoteMap = quotes.stream()
                .collect(Collectors.toMap(QuoteEntity::getQuoteDate, x -> x));
        var transactionMap = transactions.stream()
                .filter(t -> ValidationStatus.VALIDATED.equals(t.getValidationStatus()))
                .collect(Collectors.groupingBy(TransactionEntity::getTransactionDate));

        var divMap = dividends.stream()
                .filter(x -> x.getPaymentDate() != null)
                .collect(Collectors.toMap(DividendEntity::getExDividendDate, x -> x));

        var holdingDates = Stream.concat(transactionMap.keySet().stream(), divMap.keySet().stream())
                .collect(Collectors.toSet())
                .stream()
                .sorted()
                .toList();

        var reinvestTransactions = new HashMap<LocalDate, List<TransactionEntity>>();
        for (var h : holdingDates) {
            var filteredTrans = reinvestTransactions.entrySet()
                    .stream()
                    .filter(kv -> kv.getKey().toEpochDay() <= h.toEpochDay())
                    .toList();

            var qty = filteredTrans.stream()
                    .mapToDouble(kv -> kv.getValue().stream().mapToDouble(TransactionEntity::getQuantity).sum())
                    .sum();
            log.info("for date {} qty = {}", h, qty);

            if (divMap.containsKey(h)) {
                var div = divMap.get(h);
                //Do not create DIV is a validated entry exists
                if (transactionMap.getOrDefault(div.getPaymentDate(), List.of())
                        .stream()
                        .anyMatch(x -> x.getTransactionType().equals(TransactionType.DIVIDEND_CASH)
                                && x.getValidationStatus().equals(ValidationStatus.VALIDATED)))
                    continue;

                // Note: Div transaction will be created for payment date using quantity of exDiv date.
                var quote = quoteMap.get(div.getPaymentDate());
                var divTransactions = createDivTransactions(ticker, div, quote, qty);
                reinvestTransactions.computeIfAbsent(div.getPaymentDate(), k -> new ArrayList<>())
                        .addAll(divTransactions);
            }
            var tList = transactionMap.getOrDefault(h, List.of());
            reinvestTransactions.computeIfAbsent(h, k -> new ArrayList<>())
                    .addAll(tList);

        }
        return getTransactions(reinvestTransactions);
    }

    private List<TransactionEntity> getTransactions(HashMap<LocalDate, List<TransactionEntity>> transactionMap) {
        return transactionMap.values()
                .stream()
                .flatMap(this::removeDupes)
                .toList();
    }

    private Stream<TransactionEntity> removeDupes(List<TransactionEntity> tList) {
        var groups = tList.stream().collect(Collectors.groupingBy(TransactionEntity::getTransactionType));
        return groups.entrySet()
                .stream()
                .flatMap(entry ->
                        switch (entry.getKey()) {
                            case PURCHASE -> entry.getValue().stream();
                            case REINVESTMENT, DIVIDEND_CASH -> Stream.of(entry.getValue().stream()
                                    .filter(x -> x.getValidationStatus().equals(ValidationStatus.VALIDATED))
                                    .findFirst()
                                    .orElse(entry.getValue().get(0)));
                        });
    }

    private List<TransactionEntity> createDivTransactions(String ticker,
                                                          DividendEntity d,
                                                          QuoteEntity q,
                                                          double qty) {
        var divAmount = d.getAmount() * qty;
        var divTransaction = TransactionEntity.builder()
                .transactionDate(d.getPaymentDate())
                .ticker(ticker)
                .amount(divAmount)
                .price(q.getOpen())
                .transactionType(TransactionType.REINVESTMENT)
                .validationStatus(ValidationStatus.PENDING)
                .build();

        var cashTransactions = TransactionEntity.builder()
                .transactionDate(d.getPaymentDate())
                .ticker(ticker)
                .amount(divAmount)
                .transactionType(TransactionType.DIVIDEND_CASH)
                .validationStatus(ValidationStatus.PENDING)
                .build();

        return List.of(divTransaction, cashTransactions);
    }

    public List<TransactionEntity> generateReinvestmentTransactions(StockEntity stock) {
        var quotes = quoteService.getQuotes(stock.getTicker(), stock.getPositionOpenDate());
        var transactions = transactionService.getTransactionsOrderByTransactionDate(stock.getTicker());
        var dividends = dividendService.getDividends(stock.getTicker(), stock.getPositionOpenDate());
        return getReinvestmentTransactions(stock.getTicker(), quotes, transactions, dividends);
    }
}
