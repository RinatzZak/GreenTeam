package org.aston.greenteam.gateway.constant.bot;

public enum MainButtonEnum {
    CHOOSE_BUTTON("Выберите действие"),
    CONVERTER_BUTTON("Конвертация валюты"),
    GOAL_BUTTON("Цели"),
    SAVING_BUTTON("Сбережения"),
    HELP_BUTTON("Помощь");

    private final String buttonName;

    MainButtonEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
