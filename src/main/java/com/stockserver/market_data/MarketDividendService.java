package com.stockserver.market_data;

import com.stockserver.dividend.DividendEntity;

import java.time.LocalDate;
import java.util.List;

public interface MarketDividendService {
    List<DividendEntity> getDividends(String ticker, LocalDate startDate);
}
