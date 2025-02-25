package com.stockserver.quote;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface QuoteRepository extends JpaRepository<QuoteEntity, String> {
    List<QuoteEntity> findAllByTicker(String ticker);

    List<QuoteEntity> findAllByTickerAndQuoteDateGreaterThan(String ticker, LocalDate startDate);
}
