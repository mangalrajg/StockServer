package com.stockserver.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@Slf4j
@RequiredArgsConstructor
public class AbstractTransactionLoader {
    private final TransactionService transactionService;

    public List<TransactionEntity> saveTransactions(List<TransactionEntity> transactions) {
        return transactionService.saveTransactions(transactions);
    }

    public static List<TransactionEntity> readTransactionsFromCSV(String csvFilePath,
                                                                  List<BiConsumer<String[],
                                                                          TransactionEntity.TransactionEntityBuilder>> headerIndexMap) {
        List<TransactionEntity> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            boolean isFirstLine = true;
            String line;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) { // store header
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(",", -1);
                var transaction = TransactionEntity.builder();

                headerIndexMap.forEach((loader) -> {
                    loader.accept(values, transaction);
                });
                transactions.add(transaction.build());
            }
        } catch (IOException e) {
            log.error("Error parsing", e);
        }
        return transactions;
    }

}
