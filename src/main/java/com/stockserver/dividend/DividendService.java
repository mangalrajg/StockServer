package com.stockserver.dividend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DividendService {
    private final DividendRepository repo;

    public List<DividendEntity> getDividends(String ticker, LocalDate startDate) {
        return repo.findAllByTicker(ticker)
                .stream()
                .filter(x -> x.getPaymentDate().isAfter(startDate))
                .toList();
    }

    public List<DividendEntity> saveDividends(List<DividendEntity> quotes) {
        return repo.saveAll(quotes);
    }
}
