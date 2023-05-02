package org.aston.greenteam.gateway.keyboard;

import org.aston.greenteam.gateway.constant.bot.CurrencyEnum;
import org.aston.greenteam.gateway.constant.bot.GoalButtonEnum;
import org.aston.greenteam.gateway.constant.bot.SavingButtonEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineKeyboardMaker {

    public InlineKeyboardMarkup getInlineCurrencyChooseMessageButtons() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton usd = new InlineKeyboardButton();
        usd.setText(CurrencyEnum.USD.getCurrencyName());

        InlineKeyboardButton euro = new InlineKeyboardButton();
        euro.setText(CurrencyEnum.EURO.getCurrencyName());

        InlineKeyboardButton rub = new InlineKeyboardButton();
        rub.setText(CurrencyEnum.RUB.getCurrencyName());

        usd.setCallbackData(CurrencyEnum.USD.getCurrencyName());
        euro.setCallbackData(CurrencyEnum.EURO.getCurrencyName());
        rub.setCallbackData(CurrencyEnum.RUB.getCurrencyName());

        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
        keyboardButtons.add(usd);
        keyboardButtons.add(euro);
        keyboardButtons.add(rub);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtons);
        markup.setKeyboard(rowList);

        return markup;
    }

    public InlineKeyboardMarkup getInlineGoalMethodChooseMessageButtons() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton addButton = new InlineKeyboardButton();
        addButton.setText(GoalButtonEnum.ADD_BUTTON.getButtonName());

        InlineKeyboardButton getByIdButton = new InlineKeyboardButton();
        getByIdButton.setText(GoalButtonEnum.GET_BY_ID_BUTTON.getButtonName());

        InlineKeyboardButton getByUserIdButton = new InlineKeyboardButton();
        getByUserIdButton.setText(GoalButtonEnum.GET_BY_USER_ID_BUTTON.getButtonName());

        addButton.setCallbackData(GoalButtonEnum.ADD_BUTTON.getButtonName());
        getByIdButton.setCallbackData(GoalButtonEnum.GET_BY_ID_BUTTON.getButtonName());
        getByUserIdButton.setCallbackData(GoalButtonEnum.GET_BY_USER_ID_BUTTON.getButtonName());

        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
        keyboardButtons.add(addButton);
        keyboardButtons.add(getByIdButton);
        keyboardButtons.add(getByUserIdButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtons);
        markup.setKeyboard(rowList);

        return markup;
    }

    public InlineKeyboardMarkup getInlineSavingMethodChooseMessageButtons() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton addButton = new InlineKeyboardButton();
        addButton.setText(SavingButtonEnum.ADD_BUTTON.getButtonName());

        InlineKeyboardButton getButton = new InlineKeyboardButton();
        getButton.setText(SavingButtonEnum.GET_BUTTON.getButtonName());

        addButton.setCallbackData(SavingButtonEnum.ADD_BUTTON.getButtonName());
        getButton.setCallbackData(SavingButtonEnum.GET_BUTTON.getButtonName());

        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
        keyboardButtons.add(addButton);
        keyboardButtons.add(getButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtons);
        markup.setKeyboard(rowList);

        return markup;
    }
}
