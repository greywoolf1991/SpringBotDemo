package com.example.springbotdemo.models;

import com.example.springbotdemo.helpers.DoctorEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "book_list")
@Data
public class BookModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name = "doctor")
    @Enumerated
    DoctorEnum doctorEnum;
    @Column(name = "time")
    String time;
    @Column(name = "tg_id")
    String tgId;
}
