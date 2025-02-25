package com.stockserver.quote;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "t_quote")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(QuoteId.class)
public class QuoteEntity {
    @Id
    private String ticker;
    @Id
    private LocalDate quoteDate;

    @Column(nullable = false)
    private Double close;
    private Double high;
    private Double low;
    @Column(nullable = false)
    private Double open;
    private Double volume;
    private Double adjClose;
    private Double adjHigh;
    private Double adjLow;
    private Double adjOpen;
    private Double adjVolume;
    private Double divCash;
    private Double splitFactor;
}
