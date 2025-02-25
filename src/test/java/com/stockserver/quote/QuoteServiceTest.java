package com.stockserver.quote;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class QuoteServiceTest {
    @Autowired
    QuoteService service;

    @Test
    void getQuotes() {
        String ticker = "SPY";
        var q = service.getQuotes(ticker);
        var divDays = q.stream().filter(x -> x.getDivCash() > 0).toList();
        Assertions.assertNotNull(divDays);
    }

    @Test
    void saveQuotes() {
        var savedQuotes = service.saveQuotes(List.of(QuoteEntity.builder()
                .quoteDate(LocalDate.of(2020, 1, 1))
                .ticker("SPY")
                .open(1D)
                .close(1D)
                .build()));
        Assertions.assertNotNull(savedQuotes);
        Assertions.assertEquals(1, savedQuotes.size());

        var q = service.getQuotes("SPY");
        Assertions.assertNotNull(q);
        Assertions.assertEquals(1, q.size());

    }
}