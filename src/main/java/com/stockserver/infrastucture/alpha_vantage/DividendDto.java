package com.stockserver.infrastucture.alpha_vantage;

import java.util.List;

public record DividendDto(String symbol, List<DividendEntryDto> data) {
}
