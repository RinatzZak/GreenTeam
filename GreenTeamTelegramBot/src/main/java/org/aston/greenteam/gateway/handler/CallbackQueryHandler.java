package org.aston.greenteam.gateway.handler;

import lombok.extern.log4j.Log4j;
import org.aston.greenteam.gateway.constant.bot.CurrencyEnum;
import org.aston.greenteam.gateway.constant.bot.GoalButtonEnum;
import org.aston.greenteam.gateway.constant.bot.SavingButtonEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@Log4j
public class CallbackQueryHandler {

    public SendMessage processCurrencyCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();

        String data = callbackQuery.getData();
        log.debug(data);

        if (CurrencyEnum.contains(data)) {
            return new SendMessage(chatId, "Вы выбрали " + data);
        }

        return new SendMessage(chatId, "Валюта не поддерживается");
    }

    public SendMessage processGoalCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();

        String data = callbackQuery.getData();
        log.debug(data);

        if (GoalButtonEnum.contains(data)) {
            return new SendMessage(chatId, "Вы выбрали - " + data);
        }

        return new SendMessage(chatId, "Кнопка не поддерживается");
    }

    public SendMessage processSavingCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();

        String data = callbackQuery.getData();
        log.debug(data);

        if(SavingButtonEnum.contains(data)) {
            return new SendMessage(chatId, "Вы выбрали - " + data);
        }

        return new SendMessage(chatId, "Кнопка не поддерживается");
    }
}
