package com.stockserver.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "t_transaction")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TransactionId.class)
public class TransactionEntity {
    @Id
    private String ticker;
    @Id
    private LocalDate transactionDate;

    private double amount;
    private double price;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private ValidationStatus validationStatus;

    public double getQuantity() {
        return transactionType == TransactionType.DIVIDEND_CASH ? 0 : amount / price;
    }
}
