package com.stockserver.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@SpringBootTest
class CSVTransactionLoaderTest {

    final String CSV_FILE_PATH = "src/test/resources/2018-2024_ChaseInvestmentTransactions.csv";
    @Autowired
    ChaseCSVTransactionsLoader loader;

    private Path tempCsv;

//    @BeforeEach
//    void setUp() throws Exception {
//        tempCsv = Files.createTempFile("transactions", ".csv");
//        try (FileWriter writer = new FileWriter(tempCsv.toFile())) {
//            writer.write("ticker,transactionDate,transactionType,price,amount,quantity\n");
//            writer.write("AAPL,2020-01-02,BUY,10,1500,150\n");
//            writer.write("AAPL,2020-01-03,REINVESTMENT,12,150,\n");
//        }
//    }

    @Test
    void testLoadTransactionsFromCSV() throws Exception {

        List<TransactionEntity> transactions = loader.loadTransactions(CSV_FILE_PATH);
        assertThat(transactions).isNotEmpty();
    }
}