package com.example.springbotdemo.commands;

import com.example.springbotdemo.helpers.DoctorHelper;
import com.example.springbotdemo.helpers.TimeControl;
import com.example.springbotdemo.helpers.UserHelper;
import com.example.springbotdemo.models.BookModel;
import com.example.springbotdemo.models.UserModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.ResourceBundle;

@Component
public class ChooseTime implements WorKerCommand {
    String basename = "messages";
    @Override
    public SendMessage start(Update update) {
        TimeControl timeControl = new TimeControl();
        List<String> list = timeControl.getTimes();
        boolean ifThisCommand = false;
        for (String str: list){
            if (update.getMessage().getText().equals(str)) {
                ifThisCommand = true;
            }
        }
        if (!ifThisCommand){
            return null;
        }
        BookModel bookModel = new BookModel();
        bookModel.setTime(update.getMessage().getText());

        UserModel userModel = UserHelper.findUser(update.getMessage().getFrom().getId().toString());
        bookModel.setTgId(update.getMessage().getFrom().getId().toString());
        bookModel.setDoctorEnum(userModel.getDoctorEnum());

        DoctorHelper.save(bookModel);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(ResourceBundle.getBundle(basename).getString("successfullySigned"));
        return sendMessage;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }
}
