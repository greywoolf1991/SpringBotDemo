package com.example.springbotdemo.commands.bookcommands;

import com.example.springbotdemo.commands.WorKerCommand;
import com.example.springbotdemo.helpers.DoctorEnum;
import com.example.springbotdemo.helpers.DoctorHelper;
import com.example.springbotdemo.helpers.UserHelper;
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
public class DoctorBookCommand implements WorKerCommand {
    String basename = "messages";

    @Override
    public SendMessage start(Update update) {
        for (DoctorEnum e: DoctorEnum.values()) {
            if (update.getMessage().getText().equals(String.valueOf(e))){
                UserModel userModel = UserHelper.findUser(update.getMessage().getFrom().getId().toString());
                userModel.setDoctorEnum(e);
                UserHelper.saveUser(userModel);
                return sendDefaultMessage(update);
            }
        }
        return null;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(ResourceBundle.getBundle(basename).getString("chooseTime"));

        List<String> list = DoctorHelper.getFreeTimes(DoctorEnum.valueOf(update.getMessage().getText()));
        KeyboardRow k1 = new KeyboardRow();
        k1.add(new KeyboardButton(list.get(0)));
        k1.add(new KeyboardButton(list.get(1)));

        List<KeyboardRow> kR = new ArrayList<>();
        kR.add(k1);
        KeyboardRow k2 = new KeyboardRow();
        if (list.size()>2){
            for (int i = 2; i< list.size(); i++){
                k2.add(new KeyboardButton(list.get(i)));
            }
            kR.add(k2);
        }
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(kR);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}
