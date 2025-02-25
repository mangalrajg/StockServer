package com.stockserver.quote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuoteService {
    private final QuoteRepository repo;

    public List<QuoteEntity> getQuotes(String ticker) {
        return repo.findAllByTicker(ticker);
    }

    @Cacheable("quoteCache")
    public List<QuoteEntity> getQuotes(String ticker, LocalDate startDate) {
        return repo.findAllByTickerAndQuoteDateGreaterThan(ticker, startDate);
    }

    public List<QuoteEntity> saveQuotes(List<QuoteEntity> quotes) {
        return repo.saveAll(quotes);
    }
}
