package com.stockserver.infrastucture.tiingo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.Period;

@SpringBootTest
@ActiveProfiles({"test", "local"})
@Slf4j
@Disabled
class TiingoServiceTest {
    @Autowired
    private TiingoService service;

    @Test
    void getPrices() {
        var ret = service.getPrices("AAPL", LocalDate.now().minus(Period.ofDays(5)), LocalDate.now());
        log.info(ret.toString());
    }
}