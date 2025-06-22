package com.stockserver.transaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Endpoints for managing transactions")

public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    @Operation(summary = "Get all transactions")
    public List<TransactionEntity> getAllTransactions() {
        return transactionService.getTransactions();
    }

    @GetMapping("/{ticker}")
    @Operation(summary = "Get transactions by ticker")

    public List<TransactionEntity> getTransactionsByTicker(@PathVariable String ticker) {
        return transactionService.getTransactionsOrderByTransactionDate(ticker);
    }

    @PostMapping
    @Operation(summary = "Save transactions")
    public List<TransactionEntity> saveTransactions(@RequestBody List<TransactionEntity> transactions) {
        return transactionService.saveTransactions(transactions);
    }

    @PostMapping("/single")
    @Operation(summary = "Create a single transaction")
    public TransactionEntity createTransaction(@RequestBody TransactionEntity transaction) {
        return transactionService.createTransaction(transaction);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a transaction by ID")
    public TransactionEntity updateTransaction(@PathVariable Long id, @RequestBody TransactionEntity transaction) {
        return transactionService.updateTransaction(id, transaction);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a transaction by ID")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
