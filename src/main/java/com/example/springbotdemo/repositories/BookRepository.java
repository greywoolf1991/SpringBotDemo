package com.example.springbotdemo.repositories;

import com.example.springbotdemo.helpers.DoctorEnum;
import com.example.springbotdemo.models.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Long> {
    List<BookModel> findBookModelsByTgId (String id);
    List<BookModel> findBookModelsByDoctorEnum (DoctorEnum d);
}
