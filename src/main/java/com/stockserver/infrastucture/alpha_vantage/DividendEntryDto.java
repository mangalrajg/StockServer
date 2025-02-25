package com.stockserver.infrastucture.alpha_vantage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DividendEntryDto(@JsonProperty("ex_dividend_date") LocalDate exDividendDate,
                               @JsonProperty("declaration_date") String declarationDate,
                               @JsonProperty("record_date") String recordDate,
                               @JsonProperty("payment_date") String paymentDate,
                               double amount) {
}
