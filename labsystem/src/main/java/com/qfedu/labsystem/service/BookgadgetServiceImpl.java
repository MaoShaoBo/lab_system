package com.qfedu.labsystem.service;

import com.qfedu.labsystem.dao.BookgadgetMapper;
import com.qfedu.labsystem.pojo.Bookgadget;
import com.qfedu.labsystem.service.BookgadgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookgadgetServiceImpl implements BookgadgetService{
    @Autowired
    private BookgadgetMapper bookgadgetMapper;

    @Override
    public void addBookgadget(Bookgadget bookgadget) {
        bookgadgetMapper.addBookgadget(bookgadget);
    }

    @Override
    public List<Bookgadget> getBookgadgetList(Bookgadget bookgadget) {
        return bookgadgetMapper.getBookgadgetList(bookgadget);
    }

    @Override
    public List<Bookgadget> getBookgadgetListByUsername(String bookgadget_username, Integer bookgadget_state) {
        return bookgadgetMapper.getBookgadgetListByUsername(bookgadget_username, bookgadget_state);
    }

    @Override
    public void deleteBookgadget(Integer id) {
        bookgadgetMapper.deleteBookgadget(id);
    }

    @Override
    public void updateBookgadget(Bookgadget bookgadget) {
        bookgadgetMapper.updateBookgadget(bookgadget);
    }
}
