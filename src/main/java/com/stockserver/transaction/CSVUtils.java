package com.stockserver.transaction;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.function.Function;

@Slf4j
public class CSVUtils {
    public final static Function<String, LocalDate> DATE_PARSER = LocalDate::parse;
    public final static Function<String, String> STRING_PARSER = String::trim;
    public final static Function<String, TransactionType> TYPE_PARSER = TransactionType::fromString;
    public final static Function<String, Double> DOUBLE_PARSER = (String value) -> {
        if (value == null || value.isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(value);
    };
}