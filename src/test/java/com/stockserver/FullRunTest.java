package com.stockserver;

import com.stockserver.stock.StockService;
import com.stockserver.transaction.TransactionService;
import com.stockserver.transaction.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("local")
@Slf4j
@SpringBootTest
//@TestPropertySource(properties = {
//        "spring.datasource.url=jdbc:sqlite:stock_server.sqllite",
//        "spring.datasource.driver-class-name=org.sqlite.JDBC"
//})
class FullRunTest {

    @Autowired
    private ReinvestmentService reinvestmentService;
    @Autowired
    private StockService stockService;
    @Autowired
    private TransactionService transactionService;

    @Test
    void reinvestmentTest() {
        // This test is a placeholder for the reinvestment service functionality.
        // It can be expanded with actual test logic as needed.
        var stock = stockService.getStocks().stream().filter(x -> x.getTicker().equals("SPY")).findFirst()
                .orElseThrow(() -> new RuntimeException("Stock SPY not found"));

//        var transactions = transactionService.getTransactionsOrderByTransactionDate(stock.getTicker());
//        transactions.forEach(x -> x.setTransactionDate(LocalDate.of(2024, 9, 6)));
//        transactionService.saveTransactions(transactions);
        // Generate reinvestment transactions
        var reinvestTransactions =
                reinvestmentService.generateReinvestmentTransactions(stock);

        var reinv = reinvestTransactions.stream().filter(x -> x.getTransactionType() == TransactionType.REINVESTMENT)
                .toList();
        log.info("Reinvestment service test executed successfully.");
    }


}
