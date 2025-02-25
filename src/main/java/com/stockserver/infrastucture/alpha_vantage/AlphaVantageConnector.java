package com.stockserver.infrastucture.alpha_vantage;

import com.stockserver.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "alphavantage", url = "${stock-server.alpha_vantage.url}", configuration = FeignConfig.class)
public interface AlphaVantageConnector {
    @GetMapping("/query?function=DIVIDENDS")
    DividendDto getDividends(@RequestParam String symbol,
                             @RequestParam String apikey);
}
