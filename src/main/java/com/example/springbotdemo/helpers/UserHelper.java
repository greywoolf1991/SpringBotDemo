package com.example.springbotdemo.helpers;

import com.example.springbotdemo.models.UserModel;
import com.example.springbotdemo.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserHelper {
    final
    UserRepository userRepository;

    public UserHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
        helper = this;
    }

    private static UserHelper helper = null;

    public static void saveUser(UserModel userModel){
        helper.userRepository.save(userModel);
    }
    public static UserModel findUser(String tgId){
        UserModel userModel = helper.userRepository.findUserModelByTgId(tgId);
        return userModel;
    }
}
