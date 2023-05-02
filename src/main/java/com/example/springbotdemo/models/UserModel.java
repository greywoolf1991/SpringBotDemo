package com.example.springbotdemo.models;

import com.example.springbotdemo.helpers.DoctorEnum;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "telegram_user")
@Data
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "username")
    String username;

    @Column(name = "telegram_id")
    String tgId;

    @Column(name = "user_name")
    String name;

    @Column(name = "wanted_doctor")
    @Enumerated
    DoctorEnum doctorEnum;
}
