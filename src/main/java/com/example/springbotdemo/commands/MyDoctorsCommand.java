package com.example.springbotdemo.commands;

import com.example.springbotdemo.helpers.DoctorHelper;
import com.example.springbotdemo.helpers.UserHelper;
import com.example.springbotdemo.models.BookModel;
import com.example.springbotdemo.models.UserModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class MyDoctorsCommand implements WorKerCommand{
    String basename = "messages";

    @Override
    public SendMessage start(Update update) {
        if (!update.getMessage().getText().equals(ResourceBundle.getBundle(basename).getString("myDoctors"))){
            return null;
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(ResourceBundle.getBundle(basename).getString("haveDoctors"));

        List<BookModel> bookModelList = DoctorHelper.getBookList(update.getMessage().getFrom().getId().toString());
        List<KeyboardRow> kR = new ArrayList<>();
        for (BookModel b: bookModelList) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(b.getTime()+" "+b.getDoctorEnum()));
            kR.add(keyboardRow);
        }
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(kR);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }
}
