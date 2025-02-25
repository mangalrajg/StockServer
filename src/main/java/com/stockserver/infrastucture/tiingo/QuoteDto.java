package com.stockserver.infrastucture.tiingo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDto {
    private LocalDate date;

    private double close;
    private double high;
    private double low;
    private double open;
    private double volume;
    private double adjClose;
    private double adjHigh;
    private double adjLow;
    private double adjOpen;
    private double adjVolume;
    private double divCash;
    private double splitFactor;
}
