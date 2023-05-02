package org.aston.greenteam.gateway.constant.bot;

public enum GoalCommandEnum {
    CHOOSE_METHOD("Выберите действие"),
    ENTER_NAME("Введите название цели"),
    CHOOSE_CURR("Выберите валюту цели"),
    ENTER_SUM("Введите сумму цели"),
    ENTER_ID("Введите id цели"),
    WAIT("Подождите, функция в разработке"),
    GOAL_EXIT("Главная");

    private final String commandName;

    GoalCommandEnum(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
