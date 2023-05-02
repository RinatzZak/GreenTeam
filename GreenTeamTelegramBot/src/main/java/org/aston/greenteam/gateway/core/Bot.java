package org.aston.greenteam.gateway.core;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.aston.greenteam.gateway.constant.bot.ConverterCommandEnum;
import org.aston.greenteam.gateway.constant.bot.CurrencyEnum;
import org.aston.greenteam.gateway.constant.bot.GoalButtonEnum;
import org.aston.greenteam.gateway.constant.bot.GoalCommandEnum;
import org.aston.greenteam.gateway.constant.bot.MainButtonEnum;
import org.aston.greenteam.gateway.constant.bot.SavingButtonEnum;
import org.aston.greenteam.gateway.constant.bot.SavingCommandEnum;
import org.aston.greenteam.gateway.converter.ConvertingModel;
import org.aston.greenteam.gateway.goal.GoalModel;
import org.aston.greenteam.gateway.handler.CallbackQueryHandler;
import org.aston.greenteam.gateway.handler.DialogBox;
import org.aston.greenteam.gateway.keyboard.InlineKeyboardMaker;
import org.aston.greenteam.gateway.keyboard.ReplyKeyboardMaker;
import org.aston.greenteam.gateway.saving.SavingModel;
import org.aston.greenteam.gateway.state.ConverterStep;
import org.aston.greenteam.gateway.state.GoalStep;
import org.aston.greenteam.gateway.state.MenuStatus;
import org.aston.greenteam.gateway.state.SavingStep;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Log4j
@Getter
@Component
public class Bot extends TelegramLongPollingBot {
    private final String botUsername;
    private final String botToken;
    private final ReplyKeyboardMaker replyKeyboardMaker;
    private final InlineKeyboardMaker inlineKeyboardMaker;
    private final CallbackQueryHandler callbackQueryHandler;
    private Map<Long, MenuStatus> menuStatusMap = new HashMap<>();
    private Map<Long, ConverterStep> converterStepMap = new HashMap<>();
    private Map<Long, GoalStep> goalStepMap = new HashMap<>();
    private Map<Long, SavingStep> savingStepMap = new HashMap<>();
    private Map<Long, ConvertingModel> convertingModelMap = new HashMap<>();
    private Map<Long, GoalModel> goalModelMap = new HashMap<>();
    private Map<Long, SavingModel> savingModelMap = new HashMap<>();

    public Bot(
            TelegramBotsApi telegramBotsApi,
            @Value("${bot.name}") String botUsername,
            @Value("${bot.token}") String botToken,
            ReplyKeyboardMaker replyKeyboardMaker,
            InlineKeyboardMaker inlineKeyboardMaker,
            CallbackQueryHandler callbackQueryHandler) throws TelegramApiException {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.replyKeyboardMaker = replyKeyboardMaker;
        this.inlineKeyboardMaker = inlineKeyboardMaker;
        this.callbackQueryHandler = callbackQueryHandler;
        telegramBotsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private ConverterStep verifyConverterStep(Long chatId) {
        if (converterStepMap.containsKey(chatId)) {
            return converterStepMap.get(chatId);
        }
        converterStepMap.put(chatId, ConverterStep.CHOOSE_START);
        return converterStepMap.get(chatId);
    }

    private ConverterStep changeConverterStep(Long chatId, ConverterStep converterStep) {
        converterStepMap.put(chatId, converterStep);
        return converterStepMap.get(chatId);
    }

    private GoalStep verifyGoalStep(Long chatId) {
        if (goalStepMap.containsKey(chatId)) {
            return goalStepMap.get(chatId);
        }
        goalStepMap.put(chatId, GoalStep.CHOOSE_START);
        return goalStepMap.get(chatId);
    }

    private GoalStep changeGoalStep(Long chatId, GoalStep goalStep) {
        goalStepMap.put(chatId, goalStep);
        return goalStepMap.get(chatId);
    }

    private SavingStep verifySavingStep(Long chatId) {
        if (savingStepMap.containsKey(chatId)) {
            return savingStepMap.get(chatId);
        }
        savingStepMap.put(chatId, SavingStep.CHOOSE_START);
        return savingStepMap.get(chatId);
    }

    private SavingStep changeSavingStep(Long chatId, SavingStep savingStep) {
        savingStepMap.put(chatId, savingStep);
        return savingStepMap.get(chatId);
    }

    private ConvertingModel refreshConvertingModel(Long chatId) {
        ConvertingModel convertingModel = new ConvertingModel();
        convertingModelMap.put(chatId, convertingModel);
        return convertingModel;
    }

    private ConvertingModel verifyConvertingModel(Long chatId) {
        if (convertingModelMap.containsKey(chatId)) {
            return convertingModelMap.get(chatId);
        }
        throw new RuntimeException("command queue was broken");
    }

    private GoalModel verifyGoalModel(Long chatId) {
        if (goalModelMap.containsKey(chatId)) {
            return goalModelMap.get(chatId);
        }
        throw new RuntimeException("command queue was broken");
    }

    private SavingModel verifySavingModel(Long chatId) {
        if(savingModelMap.containsKey(chatId)) {
            return savingModelMap.get(chatId);
        }
        throw new RuntimeException("command queue was broken");
    }

    private DialogBox makeDialogBox(Update update) {
        Long chatId;
        Message originalMessage;
        String text;
        CallbackQuery callbackQuery;

        if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            originalMessage = update.getCallbackQuery().getMessage();
            text = update.getCallbackQuery().getMessage().getText();
            callbackQuery = update.getCallbackQuery();
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId();
            originalMessage = update.getMessage();
            text = originalMessage.getText();
            callbackQuery = null;
        } else {
            throw new RuntimeException("no message received or no text provided in message");
        }

        return new DialogBox(chatId, originalMessage, text, callbackQuery);
    }

    private void converterStarting(DialogBox dialogBox) {
        log.info("Converter starting");
        Long chatId = dialogBox.getChatId();

        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText(ConverterCommandEnum.CHOOSE_BASE_CURR.getCommandName());

        response.setReplyMarkup(inlineKeyboardMaker.getInlineCurrencyChooseMessageButtons());

        sendAnswerMessage(response);
        changeConverterStep(chatId, ConverterStep.CHOOSE_BASE_CURR);

        refreshConvertingModel(chatId);
    }

    private void goalStarting(DialogBox dialogBox) {
        log.info("Goal starting");
        Long chatId = dialogBox.getChatId();

        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText(GoalCommandEnum.CHOOSE_METHOD.getCommandName());

        response.setReplyMarkup(inlineKeyboardMaker.getInlineGoalMethodChooseMessageButtons());

        sendAnswerMessage(response);
        changeGoalStep(chatId, GoalStep.CHOOSE_METHOD);
    }

    private void savingStarting(DialogBox dialogBox) {
        log.info("Saving starting");
        Long chatId = dialogBox.getChatId();

        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText(SavingCommandEnum.CHOOSE_METHOD.getCommandName());

        response.setReplyMarkup(inlineKeyboardMaker.getInlineSavingMethodChooseMessageButtons());

        sendAnswerMessage(response);
        changeSavingStep(chatId, SavingStep.CHOOSE_METHOD);
    }

    private void converterChoosingBaseCurr(DialogBox dialogBox) {
        log.info("Converter choosing base currency");
        if (dialogBox.hasCallbackQuery()) {
            Long chatId = dialogBox.getChatId();

            CallbackQuery callbackQuery = dialogBox.getCallbackQuery();
            sendAnswerMessage(callbackQueryHandler.processCurrencyCallbackQuery(callbackQuery));

            String data = callbackQuery.getData();
            ConvertingModel convertingModel = verifyConvertingModel(chatId);
            convertingModel.setBaseCurr(CurrencyEnum.valueOf(data));

            SendMessage response = new SendMessage();
            response.setChatId(chatId);
            response.setText(ConverterCommandEnum.CHOOSE_QUOTED_CURR.getCommandName());

            response.setReplyMarkup(inlineKeyboardMaker.getInlineCurrencyChooseMessageButtons());

            sendAnswerMessage(response);
            changeConverterStep(chatId, ConverterStep.CHOOSE_QUOTED_CURR);
        }
    }

    private void goalChoosingMethod(DialogBox dialogBox) {
        //TODO
        log.info("Goal method choosing");
        if (dialogBox.hasCallbackQuery()) {
            Long chatId = dialogBox.getChatId();

            CallbackQuery callbackQuery = dialogBox.getCallbackQuery();
            sendAnswerMessage(callbackQueryHandler.processGoalCallbackQuery(callbackQuery));

            String data = callbackQuery.getData();

            SendMessage response = new SendMessage();
            response.setChatId(chatId);

            if (data.equals(GoalButtonEnum.ADD_BUTTON.getButtonName())) {
                response.setText(GoalCommandEnum.ENTER_NAME.getCommandName());
                sendAnswerMessage(response);

                refreshGoalModel(chatId);

                changeGoalStep(chatId, GoalStep.ENTER_NAME);
            } else if (data.equals(GoalButtonEnum.GET_BY_ID_BUTTON.getButtonName())) {
                response.setText(GoalCommandEnum.ENTER_ID.getCommandName());
                sendAnswerMessage(response);
                changeGoalStep(chatId, GoalStep.CHOOSE_ID);
            } else if (data.equals(GoalButtonEnum.GET_BY_USER_ID_BUTTON.getButtonName())) {
                //TODO получить все цели из микросервиса и заменить текст
                //---начало заглушки---
                response.setText(GoalCommandEnum.WAIT.getCommandName());
                //---конец заглушки---
                sendAnswerMessage(response);
                changeGoalStep(chatId, GoalStep.CHOOSE_START);
                changeMenuStatus(chatId, MenuStatus.START);
                proposeMenuOptions(dialogBox);
            }
        }
    }

    private void savingChoosingMethod(DialogBox dialogBox) {
        log.info("Saving method choosing");
        if (dialogBox.hasCallbackQuery()) {
            Long chatId = dialogBox.getChatId();

            CallbackQuery callbackQuery = dialogBox.getCallbackQuery();
            sendAnswerMessage(callbackQueryHandler.processSavingCallbackQuery(callbackQuery));

            String data = callbackQuery.getData();

            SendMessage response = new SendMessage();
            response.setChatId(chatId);

            if (data.equals(SavingButtonEnum.ADD_BUTTON.getButtonName())) {
                response.setText(SavingCommandEnum.CHOOSE_CURR.getCommandName());
                response.setReplyMarkup(inlineKeyboardMaker.getInlineCurrencyChooseMessageButtons());
                sendAnswerMessage(response);

                refreshSavingModel(chatId);

                changeSavingStep(chatId, SavingStep.CHOOSE_CURR);
            } else if (data.equals(SavingButtonEnum.GET_BUTTON.getButtonName())) {
                response.setText(SavingCommandEnum.CHOOSE_VIEW_CURR.getCommandName());
                response.setReplyMarkup(inlineKeyboardMaker.getInlineCurrencyChooseMessageButtons());

                sendAnswerMessage(response);
                changeSavingStep(chatId, SavingStep.CHOOSE_VIEW_CURR);
            }
        }
    }

    private void refreshSavingModel(Long chatId) {
        SavingModel savingModel = new SavingModel();
        savingModel.setUserId(chatId);
        savingModelMap.put(chatId, savingModel);
    }

    private void refreshGoalModel(Long chatId) {
        GoalModel goalModel = new GoalModel();
        goalModel.setUserId(chatId);
        goalModelMap.put(chatId, goalModel);
    }

    private void converterChoosingQuotedCurr(DialogBox dialogBox) {
        log.info("Converter choosing quoted currency");
        if (dialogBox.hasCallbackQuery()) {
            Long chatId = dialogBox.getChatId();

            CallbackQuery callbackQuery = dialogBox.getCallbackQuery();
            sendAnswerMessage(callbackQueryHandler.processCurrencyCallbackQuery(callbackQuery));

            ConvertingModel convertingModel = verifyConvertingModel(chatId);

            String data = callbackQuery.getData();
            CurrencyEnum quotedCurr = CurrencyEnum.valueOf(data);
            if (convertingModel.getBaseCurr().equals(quotedCurr)) {
                SendMessage response = new SendMessage();
                response.setChatId(chatId);
                response.setText(ConverterCommandEnum.CHOOSE_DIFF_QUOTED_CURR.getCommandName());

                response.setReplyMarkup(inlineKeyboardMaker.getInlineCurrencyChooseMessageButtons());

                sendAnswerMessage(response);
                changeConverterStep(chatId, ConverterStep.CHOOSE_QUOTED_CURR);
                return;
            }

            convertingModel.setQuotedCurr(CurrencyEnum.valueOf(data));

            SendMessage response = new SendMessage();
            response.setChatId(chatId);
            response.setText(ConverterCommandEnum.ENTER_BASE_CURR_SUM.getCommandName());
            sendAnswerMessage(response);
            changeConverterStep(chatId, ConverterStep.CHOOSE_BASE_CURR_SUM);
        }
    }

    private void goalChoosingCurr(DialogBox dialogBox) {
        log.info("Goal choosing currency");
        if (dialogBox.hasCallbackQuery()) {
            Long chatId = dialogBox.getChatId();

            CallbackQuery callbackQuery = dialogBox.getCallbackQuery();
            sendAnswerMessage(callbackQueryHandler.processCurrencyCallbackQuery(callbackQuery));

            GoalModel goalModel = verifyGoalModel(chatId);

            String data = callbackQuery.getData();
            CurrencyEnum curr = CurrencyEnum.valueOf(data);
            goalModel.setCurrency(curr);

            SendMessage response = new SendMessage();
            response.setChatId(chatId);
            response.setText(GoalCommandEnum.ENTER_SUM.getCommandName());
            sendAnswerMessage(response);
            changeGoalStep(chatId, GoalStep.ENTER_SUM);
        }
    }

    private void savingChoosingCurr(DialogBox dialogBox) {
        log.info("Saving choosing currency");
        if(dialogBox.hasCallbackQuery()) {
            Long chatId = dialogBox.getChatId();

            CallbackQuery callbackQuery = dialogBox.getCallbackQuery();
            sendAnswerMessage(callbackQueryHandler.processCurrencyCallbackQuery(callbackQuery));

            SavingModel savingModel = verifySavingModel(chatId);

            String data = callbackQuery.getData();
            CurrencyEnum curr = CurrencyEnum.valueOf(data);
            savingModel.setCurrency(curr);

            SendMessage response = new SendMessage();
            response.setChatId(chatId);
            response.setText(SavingCommandEnum.ENTER_SUM.getCommandName());
            sendAnswerMessage(response);
            changeSavingStep(chatId, SavingStep.ENTER_SUM);
        }
    }

    private void savingChoosingViewCurr(DialogBox dialogBox) {
        //TODO
        log.info("Saving choosing view currency");
        if(dialogBox.hasCallbackQuery()) {
            Long chatId = dialogBox.getChatId();

            CallbackQuery callbackQuery = dialogBox.getCallbackQuery();
            sendAnswerMessage(callbackQueryHandler.processCurrencyCallbackQuery(callbackQuery));

            String data = callbackQuery.getData();
            CurrencyEnum curr = CurrencyEnum.valueOf(data);

            //TODO логика общения с микросервисом по сбережениям (получить сконвертированные сбережения)
            //---начало заглушки---
            SavingModel savingModel = new SavingModel();
            savingModel.setUserId(chatId);
            savingModel.setCurrency(curr);
            //---конец заглушки---

            SendMessage response = new SendMessage();
            response.setChatId(chatId);

            response.setText(savingModel.toString());
            sendAnswerMessage(response);
            changeSavingStep(chatId, SavingStep.CHOOSE_START);
            changeMenuStatus(chatId, MenuStatus.START);
            proposeMenuOptions(dialogBox);
        }
    }

    private void goalChoosingName(DialogBox dialogBox) {
        log.info("Goal name taking");
        Long chatId = dialogBox.getChatId();
        String text = dialogBox.getText();

        GoalModel goalModel = verifyGoalModel(chatId);
        goalModel.setText(text);

        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText(GoalCommandEnum.CHOOSE_CURR.getCommandName());

        response.setReplyMarkup(inlineKeyboardMaker.getInlineCurrencyChooseMessageButtons());

        sendAnswerMessage(response);
        changeGoalStep(chatId, GoalStep.CHOOSE_CURR);
    }

    private void goalChoosingId(DialogBox dialogBox) {
        log.info("Goal id taking");
        Long chatId = dialogBox.getChatId();
        String text = dialogBox.getText();

        Long id = Long.parseLong(text);

        //TODO логика общения с микросервисом по целям (получить цель по id)
        //---начало заглушки---
        GoalModel goalModel = new GoalModel();
        goalModel.setUserId(chatId);
        goalModel.setId(id);
        //---конец заглушки---

        SendMessage response = new SendMessage();
        response.setChatId(chatId);

        response.setText(goalModel.toString());
        sendAnswerMessage(response);
        changeGoalStep(chatId, GoalStep.CHOOSE_START);
        changeMenuStatus(chatId, MenuStatus.START);
        proposeMenuOptions(dialogBox);
    }


    private void goalChoosingSum(DialogBox dialogBox) {
        //TODO
        log.info("Goal sum taking");
        Long chatId = dialogBox.getChatId();
        String text = dialogBox.getText();

        GoalModel goalModel = verifyGoalModel(chatId);

        Long sum = Long.parseLong(text);
        goalModel.setPrice(sum);

        //TODO логика общения с микросервисом по целям
        //сохранение цели через микросервис и получение id цели (либо готовой цели с новым id)

        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        //---начало заглушки---
        response.setText(goalModel.toString());
        //---конец заглушки---
        sendAnswerMessage(response);
        changeGoalStep(chatId, GoalStep.CHOOSE_START);
        changeMenuStatus(chatId, MenuStatus.START);
        proposeMenuOptions(dialogBox);
    }

    private void savingChoosingSum(DialogBox dialogBox) {
        //TODO
        log.info("Saving sum taking");
        Long chatId = dialogBox.getChatId();
        String text = dialogBox.getText();

        SavingModel savingModel = verifySavingModel(chatId);

        Double sum = Double.parseDouble(text);
        savingModel.setSum(sum);

        //TODO логика общения с микросервисом по сбережениям
        //сохранение сбережения через микросервис и получение id сбережения (либо готового сбережения с новым id)

        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        //---начало заглушки---
        response.setText(savingModel.toString());
        //---конец заглушки---
        sendAnswerMessage(response);
        changeSavingStep(chatId, SavingStep.CHOOSE_START);
        changeMenuStatus(chatId, MenuStatus.START);
        proposeMenuOptions(dialogBox);
    }

    private void converterSumAnswering(DialogBox dialogBox) {
        log.info("Converter sum answering");
        Long chatId = dialogBox.getChatId();
        String text = dialogBox.getText();

        SendMessage response = new SendMessage();
        response.setChatId(chatId);

        ConvertingModel convertingModel = verifyConvertingModel(chatId);

        //TODO логика общения с микросервисом по конвертации валюты

        Long sum = Long.parseLong(text);
        Long result = sum / 10;

        convertingModel.setBaseCurrSum(sum);
        convertingModel.setQuotedCurrSum(result);

        response.setText(convertingModel.toString());
        sendAnswerMessage(response);
        changeConverterStep(chatId, ConverterStep.CHOOSE_START);
        changeMenuStatus(chatId, MenuStatus.START);
        proposeMenuOptions(dialogBox);
    }

    private void converterDialog(DialogBox dialogBox) {
        log.info("Converter dialog");
        ConverterStep converterStep = verifyConverterStep(dialogBox.getChatId());
        switch (converterStep) {
            case CHOOSE_START:
                converterStarting(dialogBox);
                break;
            case CHOOSE_BASE_CURR:
                converterChoosingBaseCurr(dialogBox);
                break;
            case CHOOSE_QUOTED_CURR:
                converterChoosingQuotedCurr(dialogBox);
                break;
            case CHOOSE_BASE_CURR_SUM:
                converterSumAnswering(dialogBox);
                break;
        }
    }

    private void goalDialog(DialogBox dialogBox) {
        log.info("Goal dialog");
        GoalStep goalStep = verifyGoalStep(dialogBox.getChatId());
        switch (goalStep) {
            case CHOOSE_START:
                goalStarting(dialogBox);
                break;
            case CHOOSE_METHOD:
                goalChoosingMethod(dialogBox);
                break;
            case ENTER_NAME:
                goalChoosingName(dialogBox);
                break;
            case CHOOSE_CURR:
                goalChoosingCurr(dialogBox);
                break;
            case ENTER_SUM:
                goalChoosingSum(dialogBox);
                break;
            case CHOOSE_ID:
                goalChoosingId(dialogBox);
                break;
        }
    }

    private void savingDialog(DialogBox dialogBox) {
        log.info("Saving dialog");
        SavingStep savingStep = verifySavingStep(dialogBox.getChatId());
        switch (savingStep) {
            case CHOOSE_START:
                savingStarting(dialogBox);
                break;
            case CHOOSE_METHOD:
                savingChoosingMethod(dialogBox);
                break;
            case CHOOSE_CURR:
                savingChoosingCurr(dialogBox);
                break;
            case ENTER_SUM:
                savingChoosingSum(dialogBox);
                break;
            case CHOOSE_VIEW_CURR:
                savingChoosingViewCurr(dialogBox);
                break;
        }
    }

    private MenuStatus menuDialog(DialogBox dialogBox) {
        MenuStatus menuStatus = verifyMenuStatus(dialogBox.getChatId());

        if (menuStatus.equals(MenuStatus.START)) {
            proposeMenuOptions(dialogBox);
        } else {
            updateMenuStatus(dialogBox);
        }

        menuStatus = verifyMenuStatus(dialogBox.getChatId());

        return menuStatus;
    }

    private void proposeMenuOptions(DialogBox dialogBox) {
        SendMessage response = new SendMessage();
        response.setChatId(dialogBox.getChatId());
        response.setText(MainButtonEnum.CHOOSE_BUTTON.getButtonName());
        response.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        sendAnswerMessage(response);
        changeMenuStatus(dialogBox.getChatId(), MenuStatus.PROPOSAL);
    }

    private void updateMenuStatus(DialogBox dialogBox) {
        Long chatId = dialogBox.getChatId();
        String text = dialogBox.getText();
        if (text.equals(MainButtonEnum.CONVERTER_BUTTON.getButtonName())) {
            changeMenuStatus(chatId, MenuStatus.CONVERTER);
        } else if (text.equals(MainButtonEnum.GOAL_BUTTON.getButtonName())) {
            changeMenuStatus(chatId, MenuStatus.GOAL);
        } else if (text.equals(MainButtonEnum.SAVING_BUTTON.getButtonName())) {
            changeMenuStatus(chatId, MenuStatus.SAVING);
        }
    }

    private MenuStatus verifyMenuStatus(Long chatId) {
        if (menuStatusMap.containsKey(chatId)) {
            return menuStatusMap.get(chatId);
        }
        menuStatusMap.put(chatId, MenuStatus.START);
        return menuStatusMap.get(chatId);
    }

    private MenuStatus changeMenuStatus(Long chatId, MenuStatus menuStatus) {
        menuStatusMap.put(chatId, menuStatus);
        return menuStatusMap.get(chatId);
    }

    @Override
    public void onUpdateReceived(Update update) {
        //TODO диалоги, сохранение состояний, логика
        DialogBox dialogBox = makeDialogBox(update);
        log.debug(dialogBox.getText());

        MenuStatus menuStatus = menuDialog(dialogBox);

        if (menuStatus.equals(MenuStatus.CONVERTER)) {
            converterDialog(dialogBox);
        } else if (menuStatus.equals(MenuStatus.GOAL)) {
            goalDialog(dialogBox);
        } else if (menuStatus.equals(MenuStatus.SAVING)) {
            menuDialog(dialogBox);
            savingDialog(dialogBox);
        } else {
            menuDialog(dialogBox);
        }
    }

    public void sendAnswerMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                log.error(e);
            }
        }
    }
}