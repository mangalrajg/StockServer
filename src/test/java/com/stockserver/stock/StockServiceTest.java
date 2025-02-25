package com.stockserver.stock;

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
class StockServiceTest {

    @Autowired
    StockService service;

    @Test
    void saveStocks() {
        var entity = StockEntity.builder().ticker("AAPL").positionOpenDate(LocalDate.now()).build();
        var savedEntity = service.saveStocks(List.of(entity));
        Assertions.assertNotNull(savedEntity);
        Assertions.assertEquals(1, savedEntity.size());

        var q = service.getStocks();
        Assertions.assertNotNull(q);
        Assertions.assertEquals(1, q.size());

    }

    @Test
    void getStocks() {
        var stocks = service.getStocks();
        log.info(stocks.toString());
        Assertions.assertNotNull(stocks);
    }
}