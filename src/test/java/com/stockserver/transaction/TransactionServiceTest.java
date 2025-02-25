package com.stockserver.transaction;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class TransactionServiceTest {
    @Autowired
    TransactionService service;

    @Test
    void getTransactions() {
        String ticker = "SPY";
        var q = service.getTransactionsOrderByTransactionDate(ticker);
        Assertions.assertNotNull(q);
    }

    @Test
    void saveTransactions() {
        var savedTransactions = service.saveTransactions(List.of(TransactionEntity.builder()
                .transactionDate(LocalDate.of(2020, 1, 1))
                .ticker("SPY")
                .amount(1D)
                .price(1D)
                .validationStatus(ValidationStatus.PENDING)
                .transactionType(TransactionType.PURCHASE)
                .build()));
        Assertions.assertNotNull(savedTransactions);
        Assertions.assertEquals(1, savedTransactions.size());

        var t = service.getTransactionsOrderByTransactionDate("SPY");
        Assertions.assertNotNull(t);
        Assertions.assertEquals(1, t.size());

    }
}