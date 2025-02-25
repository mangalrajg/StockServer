package com.stockserver.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository repo;

    public List<TransactionEntity> getTransactionsOrderByTransactionDate(String ticker) {
        return repo.findAllByTickerOrderByTransactionDate(ticker);
    }

    public List<TransactionEntity> saveTransactions(List<TransactionEntity> transactions) {
        return repo.saveAll(transactions);
    }
}
