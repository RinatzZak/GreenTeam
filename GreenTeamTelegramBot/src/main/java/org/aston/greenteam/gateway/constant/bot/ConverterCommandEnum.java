package org.aston.greenteam.gateway.constant.bot;

public enum ConverterCommandEnum {
    CHOOSE_BASE_CURR("Выберите базовую валюту"),
    CHOOSE_QUOTED_CURR("Выберите котируемую валюту"),
    CHOOSE_DIFF_QUOTED_CURR("Выберите котируемую валюту отличную от базовой"),
    ENTER_BASE_CURR_SUM("Введите сумму базовой валюты"),
    CONVERTER_EXIT("Главная");

    private final String commandName;

    ConverterCommandEnum(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
