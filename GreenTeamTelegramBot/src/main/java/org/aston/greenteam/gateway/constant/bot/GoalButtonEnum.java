package org.aston.greenteam.gateway.constant.bot;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum GoalButtonEnum {
    ADD_BUTTON("Добавить"),
    GET_BY_ID_BUTTON("Получить по id"),
    GET_BY_USER_ID_BUTTON("Получить все"),
    HELP_BUTTON("Помощь");

    private final String buttonName;

    private static final List<String> buttonNames = Arrays.stream(values())
            .map(GoalButtonEnum::getButtonName)
            .collect(Collectors.toList());

    GoalButtonEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }

    public static boolean contains(String currency) {
        return buttonNames.contains(currency);
    }
}
