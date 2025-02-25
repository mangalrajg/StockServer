package com.stockserver.quote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class QuoteId {
    private String ticker;
    private LocalDate quoteDate;
}
