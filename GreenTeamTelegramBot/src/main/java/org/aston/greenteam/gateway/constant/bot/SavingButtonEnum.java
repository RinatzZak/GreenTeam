package org.aston.greenteam.gateway.constant.bot;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SavingButtonEnum {
    ADD_BUTTON("Добавить"),
    GET_BUTTON("Получить"),
    HELP_BUTTON("Помощь");

    private final String buttonName;

    private static final List<String> buttonNames = Arrays.stream(values())
            .map(SavingButtonEnum::getButtonName)
            .collect(Collectors.toList());

    SavingButtonEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }

    public static boolean contains(String currency) {
        return buttonNames.contains(currency);
    }
}
