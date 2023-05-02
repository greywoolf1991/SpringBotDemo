package com.example.springbotdemo.helpers;

import com.example.springbotdemo.models.BookModel;
import com.example.springbotdemo.repositories.BookRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DoctorHelper {
    final
    BookRepository bookRepository;

    private static DoctorHelper doctorHelper = null;

    public DoctorHelper(BookRepository bookRepository){
        doctorHelper=this;
        this.bookRepository = bookRepository;
    }

    public static void save(BookModel b){
        doctorHelper.bookRepository.save(b);
    }

    public static List<String> getFreeTimes(DoctorEnum d){
        TimeControl timeControl = new TimeControl();
        List<BookModel> bookModelList =
                doctorHelper.bookRepository.findBookModelsByDoctorEnum(d);
        List<String> freeTimes = timeControl.getTimes();

        List<String> list = new ArrayList<>();
        for (BookModel b: bookModelList){
            for (String str: freeTimes){
                if (b.getTime().equals(str)){
                    list.add(b.getTime());
                }
            }
        }
        freeTimes.removeAll(list);
        return freeTimes;
    }
    public  static List<BookModel> getBookList (String id){
        List<BookModel> bookModels = doctorHelper.bookRepository.findBookModelsByTgId(id);
        return bookModels;
    }
}
