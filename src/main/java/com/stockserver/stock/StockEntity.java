package com.stockserver.stock;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "t_stock")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockEntity {
    @Id
    private String ticker;

    private LocalDate positionOpenDate;
}
