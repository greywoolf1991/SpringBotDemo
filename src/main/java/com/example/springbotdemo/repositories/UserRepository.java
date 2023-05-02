package com.example.springbotdemo.repositories;

import com.example.springbotdemo.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findUserModelByTgId(String id);
}
