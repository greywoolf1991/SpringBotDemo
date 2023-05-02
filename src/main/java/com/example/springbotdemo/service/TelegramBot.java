package com.example.springbotdemo.service;

import com.example.springbotdemo.commands.*;
import com.example.springbotdemo.commands.bookcommands.DoctorBookCommand;
import com.example.springbotdemo.config.BotConfig;
import com.example.springbotdemo.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    String basename = "messages";
    UserRepository userRepository;
    BotConfig botConfig;

    public TelegramBot(UserRepository userRepository, BotConfig botConfig) {
        this.userRepository = userRepository;
        this.botConfig = botConfig;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        KeyboardRow k = new KeyboardRow();
        if (userRepository.findUserModelByTgId(update.getMessage().getFrom().getId().toString())==null){
            k.add(new KeyboardButton(ResourceBundle.getBundle(basename).getString("login")));
        }
        if (userRepository.findUserModelByTgId(update.getMessage().getFrom().getId().toString())!=null){
                k.add(new KeyboardButton(ResourceBundle.getBundle(basename).getString("myDoctors")));
        }
        k.add(new KeyboardButton(ResourceBundle.getBundle(basename).getString("withADoctor")));
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(ResourceBundle.getBundle(basename).getString("selectAnAction"));

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Collections.singletonList(k));
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        List<WorKerCommand> list = new ArrayList<>();
        list.add(new LoginCommand());
        list.add(new BookCommand());
        list.add(new DoctorBookCommand());
        list.add(new ChooseTime());
        list.add(new MyDoctorsCommand());
        SendMessage s;
        for (WorKerCommand w: list){
            if ((s = w.start(update))!=null){
                sendMessage = s;
                break;
            }
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
