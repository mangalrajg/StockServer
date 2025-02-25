package com.stockserver.infrastucture.tiingo;

import com.stockserver.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(value = "tiingo", url = "${stock-server.tiingo.url}", configuration = FeignConfig.class)
public interface TiingoConnector {
    @GetMapping("/daily/{ticker}")
    Object getMetaData(@PathVariable String ticker, @RequestParam String token);

    @GetMapping("daily/{ticker}/prices")
    List<QuoteDto> getPrices(@PathVariable String ticker,
                             @RequestParam LocalDate startDate,
                             @RequestParam LocalDate endDate,
                             @RequestParam String token);

}
