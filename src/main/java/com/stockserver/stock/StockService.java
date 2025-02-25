package com.stockserver.stock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository repo;

    public List<StockEntity> getStocks() {
        return repo.findAll();
    }

    public List<StockEntity> saveStocks(List<StockEntity> stocks) {
        return repo.saveAll(stocks);
    }
}
