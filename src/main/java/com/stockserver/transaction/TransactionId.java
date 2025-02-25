package com.stockserver.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TransactionId {
    private String ticker;
    private LocalDate transactionDate;

}
