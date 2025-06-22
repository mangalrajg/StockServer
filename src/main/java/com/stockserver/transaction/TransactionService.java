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

    public List<TransactionEntity> getTransactions() {
        return repo.findAll();
    }

    public List<TransactionEntity> saveTransactions(List<TransactionEntity> transactions) {
        return repo.saveAll(transactions);
    }

    public TransactionEntity createTransaction(TransactionEntity transaction) {
        return repo.save(transaction);
    }

    public TransactionEntity updateTransaction(Long id, TransactionEntity transaction) {
        TransactionEntity existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found with id: " + id));
        transaction.setId(id);
        return repo.save(transaction);
    }

    public void deleteTransaction(Long id) {
        repo.deleteById(id);
    }
}
