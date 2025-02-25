package com.stockserver.dividend;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "t_dividend")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(DividendId.class)
public class DividendEntity {
    @Id
    private String ticker;
    @Id
    private LocalDate paymentDate;
    private LocalDate exDividendDate;
    private LocalDate declarationDate;
    private LocalDate recordDate;
    private double amount;
}
