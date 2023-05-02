package com.example.springbotdemo.commands;

import com.example.springbotdemo.helpers.DoctorEnum;
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
public class BookCommand implements WorKerCommand{
    String basename = "messages";
    @Override
    public SendMessage start(Update update) {
        if (!update.getMessage().getText().equals(ResourceBundle.getBundle(basename).getString("withADoctor"))) {
            return null;
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());

        List<KeyboardRow> list = new ArrayList<>();
        List<String> doctors = new ArrayList<>();
        for (DoctorEnum d: DoctorEnum.values()) {
            doctors.add(String.valueOf(d));
        }
        int docInLine = 5; //количество врачей в линии на клавиатуре
        int c = doctors.size()/docInLine;
        if (doctors.size()%docInLine>0) c++;
        for (int i = 0; i < c; i++) {
            KeyboardRow kR = new KeyboardRow();
            for (int n = i*docInLine; n <doctors.size(); n++) {
                kR.add(new KeyboardButton(doctors.get(n)));
                if ((n+1)%docInLine==0) break;
            }
            list.add(kR);
        }

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(list);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setText(ResourceBundle.getBundle(basename).getString("chooseDoctor"));
        return sendMessage;
    }
    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }
}
