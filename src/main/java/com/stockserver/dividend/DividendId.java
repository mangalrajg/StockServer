package com.stockserver.dividend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class DividendId {
    private String ticker;
    private LocalDate paymentDate;
}
