package com.example.springbotdemo.commands;

import com.example.springbotdemo.helpers.UserHelper;
import com.example.springbotdemo.models.UserModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;
import java.util.ResourceBundle;

@Component
public class LoginCommand implements WorKerCommand{
    String basename = "messages";
    @Override
    public SendMessage start(Update update) {

        if (!update.getMessage().getText().equals(ResourceBundle.getBundle(basename).getString("login"))
                &&!update.getMessage().getText().equals(ResourceBundle.getBundle(basename).getString("leaveYourName"))
                &&!update.getMessage().getText().equals(ResourceBundle.getBundle(basename).getString("remainAnonymous"))){
            return null;
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(ResourceBundle.getBundle(basename).getString("selectAnAction"));
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        if (update.getMessage().getText().equals(ResourceBundle.getBundle(basename).getString("login"))){
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(ResourceBundle.getBundle(basename).getString("leaveYourName")));
            keyboardRow.add(new KeyboardButton(ResourceBundle.getBundle(basename).getString("remainAnonymous")));

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRow));
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        UserModel userModel = new UserModel();
        userModel.setName(update.getMessage().getFrom().getUserName());
        userModel.setTgId(update.getMessage().getFrom().getId().toString());
        if (update.getMessage().getText().equals(ResourceBundle.getBundle(basename).getString("remainAnonymous"))){
            sendMessage.setText(ResourceBundle.getBundle(basename).getString("userSavedAs")+" "+update.getMessage().getFrom().getUserName());
            UserHelper.saveUser(userModel);

            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(ResourceBundle.getBundle(basename).getString("withADoctor")));
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRow));
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        if (update.getMessage().getText().equals(ResourceBundle.getBundle(basename).getString("leaveYourName"))){
            sendMessage.setText(ResourceBundle.getBundle(basename).getString("userSavedAs")+" "+update.getMessage().getFrom().getFirstName());
            userModel.setName(update.getMessage().getFrom().getFirstName());
            UserHelper.saveUser(userModel);

            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(ResourceBundle.getBundle(basename).getString("withADoctor")));
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRow));
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        return sendMessage;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }
}