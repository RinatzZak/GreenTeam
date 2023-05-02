package org.aston.greenteam.gateway.constant.bot;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum CurrencyEnum {
    USD("USD"),
    EURO("EURO"),
    RUB("RUB");

    private final String currencyName;

    private static final List<String> currencyNames = Arrays.stream(values())
            .map(CurrencyEnum::getCurrencyName)
            .collect(Collectors.toList());

    CurrencyEnum(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public static boolean contains(String currency) {
        return currencyNames.contains(currency);
    }
}
