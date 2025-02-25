package com.stockserver.infrastucture.alpha_vantage;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles({"test", "local"})
@Slf4j
@Disabled
class AlphaVantageServiceTest {
    @Autowired
    private AlphaVantageService service;

    @Test
    void getDividends() {
        var ret = service.getDividends("SPY", LocalDate.of(2018, 1, 1));
        log.info(ret.toString());
        Assertions.assertNotNull(ret);

    }
}