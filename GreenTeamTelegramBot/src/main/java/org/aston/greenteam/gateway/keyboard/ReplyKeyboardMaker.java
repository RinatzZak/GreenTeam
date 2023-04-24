package org.aston.greenteam.gateway.keyboard;

import org.aston.greenteam.gateway.constant.bot.MainButtonEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyKeyboardMaker {

    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        KeyboardRow convertRow = new KeyboardRow();
        convertRow.add(new KeyboardButton(MainButtonEnum.CONVERTER_BUTTON.getButtonName()));

        KeyboardRow goalRow = new KeyboardRow();
        goalRow.add(new KeyboardButton(MainButtonEnum.GOAL_BUTTON.getButtonName()));

        KeyboardRow savingRow = new KeyboardRow();
        savingRow.add(new KeyboardButton(MainButtonEnum.SAVING_BUTTON.getButtonName()));

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        keyboardRowList.add(convertRow);
        keyboardRowList.add(goalRow);
        keyboardRowList.add(savingRow);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(keyboardRowList);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(false);

        return markup;
    }
}
