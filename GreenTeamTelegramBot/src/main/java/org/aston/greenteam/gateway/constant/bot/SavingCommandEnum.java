package org.aston.greenteam.gateway.constant.bot;

public enum SavingCommandEnum {
    CHOOSE_METHOD("Выберите действие"),
    CHOOSE_CURR("Выберите валюту сбережения"),
    ENTER_SUM("Введите сумму сбережения"),
    CHOOSE_VIEW_CURR("Выберите валюту представления");

    private final String commandName;

    SavingCommandEnum(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
