package com.qfedu.labsystem.service;

import com.qfedu.labsystem.pojo.Bookgadget;
import java.util.List;

public interface BookgadgetService {
    void addBookgadget(Bookgadget bookgadget);
    List<Bookgadget> getBookgadgetList(Bookgadget bookgadget);
    List<Bookgadget> getBookgadgetListByUsername(String bookgadget_username, Integer bookgadget_state);
    void deleteBookgadget(Integer id);
    void updateBookgadget(Bookgadget bookgadget);
}
