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
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticker;
    private LocalDate transactionDate;
    private Double amount;
    private Double price;
    private Double quantity;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private ValidationStatus validationStatus;

    public static class TransactionEntityBuilder {
        public boolean hasPrice() {
            return Math.abs(this.price) > 0.0001D;
        }

        public boolean hasQuantity() {
            return Math.abs(this.quantity) > 0.0001D;
        }

        public boolean hasAmount() {
            return Math.abs(this.amount) > 0.0001D;
        }

        public TransactionType getTransactionType() {
            return transactionType;
        }
    }

    @PrePersist
    public void setMissingValues() {
        if (transactionType == TransactionType.DIVIDEND_CASH) {
            this.amount = 0D;
            this.price = 0D;
            this.quantity = 0D;
        } else {
            if (this.amount == null) {
                this.amount = this.quantity * this.price;
            }
            if (this.price == null) {
                this.price = this.amount / this.quantity;
            }
            if (this.quantity == null) {
                this.quantity = this.amount / this.price;
            }
        }
    }
}
