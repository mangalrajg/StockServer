package com.stockserver;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Slf4j
@SpringBootTest
class StockServerApplicationTests {

    @Test
    void contextLoads() {
    }

}
