package com.stockserver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@Slf4j
@EnableFeignClients
@RequiredArgsConstructor
public class StockServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockServerApplication.class, args);
    }
}
