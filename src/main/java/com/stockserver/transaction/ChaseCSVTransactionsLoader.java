package com.stockserver.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiConsumer;

@Service
@Slf4j
public class ChaseCSVTransactionsLoader extends AbstractTransactionLoader {
    public ChaseCSVTransactionsLoader(TransactionService transactionService) {
        super(transactionService);
    }


    public List<TransactionEntity> loadTransactions(String csvFilePath) {
        BiConsumer<String[], TransactionEntity.TransactionEntityBuilder> DESCRIPTION_PARSER = (values, builder) -> {
            if (builder.getTransactionType().equals(TransactionType.REINVESTMENT)) {
                String description = values[2];
                if (!builder.hasPrice()) {
                    var pattern = java.util.regex.Pattern.compile("@\\s(\\d+\\.\\d+)");
                    var matcher = pattern.matcher(description);
                    if (matcher.find()) {
                        double price = Double.parseDouble(matcher.group(1));
                        builder.price(price);
                    }
                }
            }
            if (builder.getTransactionType().equals(TransactionType.DIVIDEND_CASH)) {
                return;
            }

            if (builder.hasAmount() && builder.hasPrice() && builder.hasQuantity()) {
                log.debug("All good with transaction");
            } else {
                log.warn("Transaction missing amount, price or quantity: {}", builder);
            }
        };

        final List<BiConsumer<String[], TransactionEntity.TransactionEntityBuilder>> headerMap = List.of(
                (values, builder) -> builder.transactionDate(CSVUtils.DATE_PARSER.apply(values[0])),
                (values, builder) -> builder.ticker(CSVUtils.STRING_PARSER.apply(values[1])),
                (values, builder) -> builder.transactionType(CSVUtils.TYPE_PARSER.apply(values[4])),
                (values, builder) -> builder.quantity(CSVUtils.DOUBLE_PARSER.apply(values[5])),
                (values, builder) -> builder.price(CSVUtils.DOUBLE_PARSER.apply(values[6])),
                (values, builder) -> builder.amount(CSVUtils.DOUBLE_PARSER.apply(values[7])),
                DESCRIPTION_PARSER
        );
        List<TransactionEntity> transactions = readTransactionsFromCSV(csvFilePath, headerMap);
        return saveTransactions(transactions);
    }
}
