package com.stockserver.transaction;

import java.util.Arrays;

public enum TransactionType {
    BUY("BUY"),
    SELL("SELL"),
    STK_SPLT("STK SPLT"),
    CIL("CIL"),
    EXCHANGE("EXCHANGE"),
    REINVESTMENT("REINVESTMENT", "REINVEST"),
    DIVIDEND_CASH("DIVIDEND_CASH", "DIVIDEND");

    private final String[] aliases;

    TransactionType(String... aliases) {
        this.aliases = aliases;
    }

    public static TransactionType fromString(String value) {
        String normalized = value.trim().toUpperCase();
        return Arrays.stream(TransactionType.values())
                .filter(type -> Arrays.stream(type.aliases)
                        .anyMatch(alias -> alias.equalsIgnoreCase(normalized)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown TransactionType: " + value));
    }
}
