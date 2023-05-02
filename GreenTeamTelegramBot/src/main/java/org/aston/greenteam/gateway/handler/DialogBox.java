package org.aston.greenteam.gateway.handler;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Getter
@Setter
public class DialogBox {
    private Long chatId;
    private Message originalMessage;
    private String text;
    private CallbackQuery callbackQuery;

    public DialogBox() {
    }

    public DialogBox(Long chatId, Message originalMessage, String text, CallbackQuery callbackQuery) {
        this.chatId = chatId;
        this.originalMessage = originalMessage;
        this.text = text;
        this.callbackQuery = callbackQuery;
    }

    public boolean hasCallbackQuery() {
        return callbackQuery != null;
    }
}
