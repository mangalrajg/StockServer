package com.stockserver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class Holding {
    private LocalDate date;
    private String ticker;
    private Double totalQuantity;
}
