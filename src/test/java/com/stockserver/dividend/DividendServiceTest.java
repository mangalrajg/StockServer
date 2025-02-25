package com.stockserver.dividend;

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
class DividendServiceTest {

    @Autowired
    DividendService service;

    @Test
    void getDividends() {
        String ticker = "SPY";
        var q = service.getDividends(ticker, LocalDate.now());
        Assertions.assertNotNull(q);

    }

    @Test
    void saveDividends() {
        var savedDivs = service.saveDividends(List.of(DividendEntity.builder()
                .exDividendDate(LocalDate.of(2020, 1, 1))
                .ticker("SPY")
                .paymentDate(LocalDate.of(2020, 1, 10))
                .build()));
        Assertions.assertNotNull(savedDivs);
        Assertions.assertEquals(1, savedDivs.size());

        var d1 = service.getDividends("SPY", LocalDate.now());
        Assertions.assertNotNull(d1);
        Assertions.assertEquals(0, d1.size());

        var d2 = service.getDividends("SPY", LocalDate.of(2019, 1, 1));
        Assertions.assertNotNull(d2);
        Assertions.assertEquals(1, d2.size());
    }
}